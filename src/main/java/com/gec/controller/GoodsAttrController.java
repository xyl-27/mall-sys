package com.gec.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.components.FileTemplate;
import com.gec.domain.bo.GoodsAttrBO;
import com.gec.domain.entity.GoodsAttr;
import com.gec.service.IGoodsAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/GoodsAttr")
public class GoodsAttrController extends BaseController {

    @Autowired
    private IGoodsAttrService goodsAttrService;

    /**
     * 获取商品属性列表
     * @param page 页码
     * @param limit 每页数量
     * @param map 查询条件
     * @return 分页结果
     */
    @PostMapping(
            value = "/list/{page}/{limit}",
            produces = "application/json;charset=UTF-8"
    )
    public R list(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, @RequestBody Map map) {
        try {
            //1. 封装分页参数
            Page _page = newPage(page, limit);
            //2. 调用业务层查询
            System.out.println("!!!!!!!!!!!");
            System.out.println(map);
            System.out.println("!!!!!!!!!!!");
            IPage<GoodsAttrBO> retPage = goodsAttrService.listGoodsAttr(_page, map);
            //3. 返回结果
            return R.convertPage(retPage);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 添加商品属性
     * @param goodsAttr 商品属性实体
     * @return 操作结果
     */
    @PostMapping(
            value = "/addGoodsAttr",
            produces = "application/json;charset=UTF-8"
    )
    public R addGoodsAttr(@RequestBody GoodsAttr goodsAttr) {
        try {
            goodsAttrService.addGoodsAttr(goodsAttr);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 更新商品属性
     * @param goodsAttr 商品属性实体
     * @return 操作结果
     * 同时支持PUT和POST方法，解决可能存在的请求方法转换问题
     */
    @RequestMapping(
            value = "/updateGoodsAttr",
            method = {RequestMethod.PUT, RequestMethod.POST},
            produces = "application/json;charset=UTF-8"
    )
    public R updateGoodsAttr(@RequestBody GoodsAttr goodsAttr) {
        try {
            // 验证ID是否存在
            if (goodsAttr.getId() == null) {
                throw new RuntimeException("商品属性ID不能为空");
            }
            
            goodsAttrService.updateGoodsAttr(goodsAttr);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 删除商品属性
     * @param id 商品属性ID
     * @return 操作结果
     */
    @DeleteMapping(
            value = "/deleteGoodsAttr/{id}",
            produces = "application/json;charset=UTF-8"
    )
    public R deleteGoodsAttr(@PathVariable("id") Integer id) {
        try {
            // 验证ID是否存在
            if (id == null) {
                throw new RuntimeException("商品属性ID不能为空");
            }
            
            goodsAttrService.deleteGoodsAttr(id);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 根据ID获取商品属性
     * @param id 商品属性ID
     * @return 商品属性信息
     */
    @GetMapping(
            value = "/getGoodsAttr/{id}",
            produces = "application/json;charset=UTF-8"
    )
    public R getGoodsAttr(@PathVariable("id") Integer id) {
        try {
            GoodsAttr goodsAttr = goodsAttrService.getById(id);
            if (goodsAttr == null) {
                throw new RuntimeException("未找到商品属性信息");
            }
            return R.ok(goodsAttr);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 异常处理
     */
    @ExceptionHandler
    public void exceptionHandler(Exception e, HttpServletResponse response) {
        R.err(e).write(response);
    }

    @Override
    protected FileTemplate getFileTemplate() {
        return null;
    }
}
