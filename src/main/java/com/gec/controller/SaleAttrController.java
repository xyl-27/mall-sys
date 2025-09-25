package com.gec.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.components.FileTemplate;
import com.gec.domain.bo.SaleAttrBO;
import com.gec.domain.entity.SaleAttr;
import com.gec.service.ISaleAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/SaleAttr")
public class SaleAttrController extends BaseController {

    @Autowired
    private ISaleAttrService saleAttrService;

    /**
     * 获取销售属性列表
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
            IPage<SaleAttrBO> retPage = saleAttrService.listSaleAttr(_page, map);
            //3. 返回结果
            return R.convertPage(retPage);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 添加销售属性
     * @param saleAttr 销售属性实体
     * @return 操作结果
     */
    @PostMapping(
            value = "/addSaleAttr",
            produces = "application/json;charset=UTF-8"
    )
    public R addSaleAttr(@RequestBody SaleAttr saleAttr) {
        try {
            saleAttrService.addSaleAttr(saleAttr);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 更新销售属性
     * @param saleAttr 销售属性实体
     * @return 操作结果
     * 同时支持PUT和POST方法，解决可能存在的请求方法转换问题
     */
    @RequestMapping(
            value = "/updateSaleAttr",
            method = {RequestMethod.PUT, RequestMethod.POST},
            produces = "application/json;charset=UTF-8"
    )
    public R updateSaleAttr(@RequestBody SaleAttr saleAttr) {
        try {
            // 验证ID是否存在
            if (saleAttr.getId() == null) {
                throw new RuntimeException("销售属性ID不能为空");
            }
            
            saleAttrService.updateSaleAttr(saleAttr);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 删除销售属性
     * @param id 销售属性ID
     * @return 操作结果
     */
    @DeleteMapping(
            value = "/deleteSaleAttr/{id}",
            produces = "application/json;charset=UTF-8"
    )
    public R deleteSaleAttr(@PathVariable("id") Integer id) {
        try {
            // 验证ID是否存在
            if (id == null) {
                throw new RuntimeException("销售属性ID不能为空");
            }
            
            saleAttrService.deleteSaleAttr(id);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 根据ID获取销售属性
     * @param id 销售属性ID
     * @return 销售属性信息
     */
    @GetMapping(
            value = "/getSaleAttr/{id}",
            produces = "application/json;charset=UTF-8"
    )
    public R getSaleAttr(@PathVariable("id") Integer id) {
        try {
            SaleAttr saleAttr = saleAttrService.getById(id);
            if (saleAttr == null) {
                throw new RuntimeException("未找到销售属性信息");
            }
            return R.ok(saleAttr);
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
