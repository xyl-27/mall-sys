package com.gec.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.components.FileTemplate;
import com.gec.domain.entity.GoodsAttr;
import com.gec.domain.vo.GoodsAttrVO;
import com.gec.service.IGoodsAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/GoodsAttr")
public class GoodsAttrController extends BaseController {

    @Autowired
    private IGoodsAttrService goodsAttrService;

    @RequestMapping(value = "/list/{page}/{limit}")
    public R list(@PathVariable Integer page,
                 @PathVariable Integer limit,
                 @RequestBody Map data) {
        Page _page = newPage(page, limit);
        Page retPage = (Page) goodsAttrService.listGoodsAttr(_page, data);
        List list = retPage.getRecords();
        System.out.println("!!!!!!!!!!!!!!!");
        System.out.println(list);
        System.out.println(page);
        System.out.println("!!!!!!!!!!!!!!!");
        return R.ok(retPage);
    }

    @PostMapping(value = "/add")
    public R add(@RequestBody GoodsAttrVO attrVO) {
        goodsAttrService.addGoodsAttr(attrVO);

        return R.ok();
    }

    @PostMapping(value = "/update")
    public R update(@RequestBody GoodsAttrVO attrVO) {
        goodsAttrService.updateGoodsAttr(attrVO);
        return R.ok();
    }

    @PutMapping(value = "/delete/{id}")
    public R delete(@PathVariable("id") Integer id) {
        goodsAttrService.deleteGoodsAttr(id);
        return R.ok();
    }



    @ExceptionHandler(Exception.class)
    public R exceptionFallback(Exception E) {
        E.printStackTrace();
        return R.err(E);
    }

    @Override
    protected FileTemplate getFileTemplate() {
        return null;
    }

}