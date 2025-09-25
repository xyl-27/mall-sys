package com.gec.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gec.components.FileTemplate;
import com.gec.controller.R;
import com.gec.domain.entity.Brand;
import com.gec.domain.vo.BrandCategoryVO;
import com.gec.domain.vo.BrandVO;
import com.gec.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import com.gec.utils.FileUtils;

@RestController
@RequestMapping("/Brand")
public class BrandController extends BaseController {
    @Autowired
    private IBrandService brandService;

    //1.获取列表【POST请求】
    @PostMapping(value = "/list/{page}/{limit}")
    public R list(
        @PathVariable("page") Integer page,
        @PathVariable("limit") Integer limit,
        @RequestBody Map data) {
        //1.封装 Page 参数(BaseController方法)
        Page _page = newPage(page, limit);
        //2.调用业务层方法查询。
        IPage retPage = brandService.listBrand(_page, data);
        //3.返回结果 R
        return R.convertPage( retPage );
    }

    /*
    2. 设置状态
    此 WEB 接口主要用于设置品牌在商城首页的显示状态。
    条件: id = 输入ID
    修改: show_status = 输入状态
    */
    @PostMapping(value = "/setStatus")
    public R setStatus(
            @RequestBody Brand brand ){
        //1.创建更新条件设置器
        UpdateWrapper<Brand> UW = new UpdateWrapper<>();
        //2.设置相关条件 - 使用前端传入的参数值
        UW.eq("id", brand.getId());
        UW.set("show_status", brand.getShowStatus());
        //3.调用通用方法来更新。
        boolean ret = brandService.update(UW);
        //4.判断执行结果。
        if( ret ){
            return R.ok();
        }else{
            throw new RuntimeException("设置品牌状态失败。");
        }
    }


    //3.添加品牌
    @PostMapping(value = "addBrand")
    public R addBrand(@RequestBody Brand brand) {
        //1.调用通用方法来实现保存。
        boolean ret = brandService.save(brand);
        //2.判断执行结果
        if (!ret) {
            throw new RuntimeException("新增品牌失败。");
        }
        return R.ok();
    }

    //4.更新品牌
    @PutMapping(value = "updateBrand")
    public R updateBrand(@RequestBody Brand brand) {
        // 调用服务层方法执行更新（包含表单校验）
        brandService.updateBrand(brand);
        return R.ok().put("message", "品牌更新成功");
    }
    
    //7.删除品牌
    @DeleteMapping(value = "deleteBrand/{id}")
    public R deleteBrand(@PathVariable("id") Integer id) {
        // 调用服务层方法执行删除（包含关联数据检查）
        brandService.deleteBrand(id);
        return R.ok().put("message", "品牌删除成功");
    }

    //5.关联品牌与类别
    //关联操作
    @PostMapping(value = "associateCategory")
    public R associateCategory(@RequestBody BrandCategoryVO bcVO) {
        //1. 调用 service 实现数据关联。
        brandService.associateCategory(bcVO);
        //2. 返回成功信息。
        return R.ok();
    }

    //异常处理
    @ExceptionHandler
    public void exceptionHandler(
            Exception e, HttpServletResponse resp) {
        R.err(e).write(resp);
    }

    //6.文件上传
    @Autowired
    private FileTemplate fileTemplate;
    private String SUB_DIR = "brand";
    @Override
    protected FileTemplate getFileTemplate() {
        return fileTemplate;
    }

    /**
     * (PS)此方法主要用于生成一个随机的文件名。
     * (由数字与英文所组成的随机序列)
     */
    private String makeNewName(MultipartFile mFile) {
        //1.生成 UUID 作为文件名。
        String UUID = FileUtils.makeUUID();
        //2.拿原始的文件名。
        String fileName = mFile.getOriginalFilename();
        //3.获取原始扩展名。
        String extName = FileUtils.extName(fileName);
        //4.返回新的文件名。
        return UUID + extName;
    }

    /**
     * 此方法可用于展示指定的图片。
     * 1.它会去读取服务器本地的图片。
     * 2.再将其输出到响应缓冲中
     * 3.发送给前端展示出来。
     */
    // 主路径 - 正确的拼写
    @GetMapping("/showImg/{imgName}")
    public void showImg(
            @PathVariable("imgName") String imgName,
            HttpServletResponse resp
    ) {
        try {
            //1.调用 BaseController 方法来输出文件。
            // (向设备端输出文件)
            outFile(resp, SUB_DIR, imgName);
        } catch (Exception e) {
            e.printStackTrace();
            send404File(resp);
        }
    }

    // 兼容路径 - 解决用户常见的拼写错误（将I写成l）
    @GetMapping("/showlmg/{imgName}")
    public void showlmg(
            @PathVariable("imgName") String imgName,
            HttpServletResponse resp
    ) {
        // 直接调用上面的showImg方法，重用相同的逻辑
        showImg(imgName, resp);
    }

    /*
     * 相关问题:
     * 1.file 表示什么，与哪个参数相关。
     * 2.MultipartFile 是一个什么对象。
     */
    @PostMapping("/upload")
    public R upload(
            @RequestParam("file") MultipartFile mFile,
            @RequestParam("userId") Integer userId,
            @RequestParam(value = "useOriginalName", defaultValue = "false") Boolean useOriginalName) {
        //1.判断文件是否为空。
        boolean isEmpty = mFile.isEmpty();
        if (isEmpty) {
            throw new RuntimeException("文件不能为空。");
        }
        //2.给文件上传模板类设置文件上传实体。
        fileTemplate.setMultipartFile(mFile);
        //3.生成文件名称。
        String newName;
        if (useOriginalName) {
            // 使用原始文件名
            newName = mFile.getOriginalFilename();
        } else {
            // 生成UUID文件名
            newName = makeNewName(mFile);
        }
        //4.拼接图片的地址。(3个杠)
        String logoUrl = "/Brand/showImg/" + newName;
        try {
            //5.调用方法可以直接写入本地磁盘。
            fileTemplate.saveFile(SUB_DIR, newName);
            //6.返回两组参数给前端。
            return R.ok()
                    .put("logoUrl", logoUrl)
                    .put("fileName", newName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传异常。");
        }
    }

    @PostMapping("/listByCategory/{page}/{limit}")
    public R listByCategory(
            @PathVariable("page") Integer page,
            @PathVariable("limit") Integer limit,
            @RequestBody Map data ) {
        Page _page = new Page(page, limit);
        IPage<BrandVO> retPage = brandService.getListByCategory( _page, data );
        return R.convertPage(retPage);
    }

}
