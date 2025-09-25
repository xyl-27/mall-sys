package com.gec.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.components.FileTemplate;
import com.gec.domain.bo.AttrGroupBO;
import com.gec.domain.entity.AttrGroup;
import com.gec.service.IAttrGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/AttrGroup")
public class AttrGroupController extends BaseController {
    @Autowired
    private IAttrGroupService attrGroupService;

    /**
     * 获取属性分组列表
     * @param page 页码
     * @param limit 每页数量
     * @param map 查询条件
     * @return 分页结果
     */
    @PostMapping(
            value = "/list/{page}/{limit}",
            produces = "application/json;charset=UTF-8"
    )
    public R list(@PathVariable("page") Integer page, 
                 @PathVariable("limit") Integer limit, 
                 @RequestBody Map map){
        try {
            Page _page = newPage(page, limit);
            IPage<AttrGroupBO> retPage = attrGroupService.listAttrGroup(
                    _page, map );
            return R.convertPage(retPage);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 添加属性分组
     * @param attrGroup 属性分组实体
     * @return 操作结果
     */
    @PostMapping(
            value = "/addAttrGroup",
            produces = "application/json;charset=UTF-8"
    )
    public R addAttrGroup( @RequestBody AttrGroup attrGroup ) {
        try {
            attrGroupService.addAttrGroup( attrGroup );
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 更新属性分组
     * @param attrGroup 属性分组实体
     * @return 操作结果
     * 同时支持PUT和POST方法，解决可能存在的请求方法转换问题
     */
    @RequestMapping(
            value = "/updateAttrGroup",
            method = {RequestMethod.PUT, RequestMethod.POST},
            produces = "application/json;charset=UTF-8"
    )
    public R updateAttrGroup( @RequestBody AttrGroup attrGroup ) {
        try {
            // 验证参数是否存在
            if (attrGroup == null) {
                throw new RuntimeException("属性分组不能为空");
            }
            // 验证ID是否存在
            if (attrGroup.getId() == null) {
                throw new RuntimeException("属性分组ID不能为空");
            }
            
            attrGroupService.updateAttrGroup( attrGroup );
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 删除属性分组
     * @param id 属性分组ID
     * @return 操作结果
     */
    @DeleteMapping(
            value = "/deleteAttrGroup/{id}",
            produces = "application/json;charset=UTF-8"
    )
    public R deleteAttrGroup( @PathVariable("id") Integer id ) {
        try {
            // 验证ID是否存在
            if (id == null) {
                throw new RuntimeException("属性分组ID不能为空");
            }
            
            attrGroupService.deleteAttrGroup(id);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    /**
     * 根据ID获取属性分组
     * @param id 属性分组ID
     * @return 属性分组信息
     */
    @GetMapping(
            value = "/getAttrGroup/{id}",
            produces = "application/json;charset=UTF-8"
    )
    public R getAttrGroup( @PathVariable("id") Integer id ) {
        try {
            // 验证ID是否存在
            if (id == null) {
                throw new RuntimeException("属性分组ID不能为空");
            }
            
            AttrGroup attrGroup = attrGroupService.getById(id);
            if (attrGroup == null) {
                throw new RuntimeException("未找到属性分组信息");
            }
            return R.ok(attrGroup);
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
