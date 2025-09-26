package com.gec.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.components.FileTemplate;
import com.gec.domain.entity.GoodsAttrGroup;
import com.gec.service.IAttrGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/AttrGroup")
public class AttrGroupController extends BaseController{

    @Autowired
    private IAttrGroupService attrGroupService;

    //Link: http://localhost:8095/mall-pms/AttrGroup/list/1/10/11
    //Vue: getGoodsAttrList( page, limit, param );
    @RequestMapping(value="/list/{page}/{limit}")
    public R list( @PathVariable("page") Integer page,
                   @PathVariable("limit") Integer limit,
                   @RequestBody Map data ){
        Page _page = newPage(page, limit);
        Page retPage = (Page) attrGroupService.listAttrGroup(
                _page, data );
        return R.ok( retPage );
    }

    @PostMapping(value="/add")
    public R add( @RequestBody GoodsAttrGroup attrGroup ) {
        System.out.println("+-----------------------------------------------+");
        System.out.println( attrGroup );
        System.out.println("+-----------------------------------------------+");
        attrGroupService.addAttrGroup( attrGroup );
        return R.ok();
    }

    @PutMapping(value="/update")
    public R update( @RequestBody GoodsAttrGroup attrGroup ) {
        attrGroupService.updateAttrGroup( attrGroup );
        return R.ok();
    }

    @PutMapping(value="/delete/{id}")
    public R delete( @PathVariable("id") Integer id ) {
        attrGroupService.deleteById(id);
        return R.ok();
    }

    @ExceptionHandler(Exception.class)
    public R exceptionFallback(Exception E){
        E.printStackTrace();
        return R.err( E );
    }

    @Override
    protected FileTemplate getFileTemplate() {
        return null;
    }
}
