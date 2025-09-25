package com.gec.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gec.components.FileTemplate;
import com.gec.domain.entity.Category;
import com.gec.domain.entity.Node;
import com.gec.domain.vo.CategoryBrandVO;
import com.gec.service.ICategoryService;
import com.gec.controller.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/Category")
public class CategoryController extends BaseController {
    @Autowired
    private ICategoryService categoryService;

    //1.获取列表
    @GetMapping("/list")
    public R list() {
        List<Node> nodes = null;
        nodes = categoryService.listCategory();
        return R.ok( nodes );
    }

    //2.添加类别
    @PostMapping("/addCategory")
    public R addCategory(@RequestBody Category category){
        //{1}设置当前类别的上代 ID 序列
        setPids( category );
        //{2}调用 mybatis-plus 方法实现保存。
        boolean ret = categoryService.save(category);
        if( !ret ){
            throw new RuntimeException("新增类别失败。");
        }
        return R.ok(); //{4}成功返回成功消息。
    }

    //{1}新建的方法
    private void setPids(Category category){
        //{1}获取父 ID.
        Integer parID = category.getParentId();
        //{2}处理根节点特殊情况
        if (parID == 0) {
            // 根节点的pids就是"0"
            category.setPIds("0");
            // 对于一级类别，设置parent_id为null，避免外键约束问题
            category.setParentId(null);
        } else {
            //{3}从数据库中获取父节点的上代 ID 序列。
            String pids = categoryService.getPids(parID);
            //{4}拼接 ids 序列。
            if (pids != null) {
                category.setPIds(pids + "," + parID);
            } else {
                // 安全起见，如果pids为null，设置默认值
                category.setPIds("0," + parID);
            }
        }
    }


    //3.更新类别
    @PutMapping("/updateCategory")
    public R updateCategory(@RequestBody Category category) {
        //{1}设置当前类别的上代 ID 序列
        setPids(category);
        //{2}创建更新条件设置器
        UpdateWrapper<Category> UW = new UpdateWrapper<>();
        UW.eq("id", category.getId());
        //{3}调用通用方法实现更新操作。
        boolean ret = categoryService.update(category, UW);
        //{4}判定执行结果
        if (!ret) {
            throw new RuntimeException("更新类别失败。");
        }
        //{5}返回成功消息
        return R.ok();
    }



    //5.删除类别
    @DeleteMapping("/deleteCategory/{id}")
    public R deleteCategory(@PathVariable("id") Integer categoryId) {
        // 检查类别是否可以删除
        boolean canDelete = categoryService.canDeleteCategory(categoryId);
        if (!canDelete) {
            throw new RuntimeException("该类别存在关联的子类别、品牌或商品，无法删除");
        }
        
        // 执行删除操作
        categoryService.deleteCategory(categoryId);
        return R.ok().put("message", "类别删除成功");
    }


    //6.获取 Pids(特殊接口)
    //测试地址: http://localhost:8090/mall-sys/Category/getPids/16
    @GetMapping(value="/getPids/{id}")
    public R getPids(@PathVariable("id") Integer id) {
        //{1}声明数组
        Integer[] ids = {};
        //{2}获取 pid 的数组
        ids = categoryService.getPidsArr( id );
        //{3}封装数组, 并返回
        return R.ok( ids );
    }


    //7.设置关联
    @PostMapping(value="/associateBrand")
    public R associateBrand(@RequestBody CategoryBrandVO cbVO) {
        //{1}调用业务方法来实现品牌的关联
        categoryService.associateBrand( cbVO );
        //{2}返回 R 封装结果
        return R.ok();
    }

    //8.待定方法。


    @ExceptionHandler
    public void exceptionHandler(
            Exception e, HttpServletResponse resp) {
        e.printStackTrace();
        R.err( e ).write( resp );
    }
    @Override
    protected FileTemplate getFileTemplate() {
        return null;
    }
}
