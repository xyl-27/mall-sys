package com.gec.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.domain.bo.GoodsAttrBO;
import com.gec.domain.entity.GoodsAttr;

import java.util.Map;

public interface IGoodsAttrService
        extends IService<GoodsAttr> {

    IPage<GoodsAttrBO> listGoodsAttr(
            Page page, Map<String, Object> data);
    void addGoodsAttr(GoodsAttr goodsAttr);
    void updateGoodsAttr(GoodsAttr goodsAttr);
    void deleteGoodsAttr(Integer id);

}

/*
    模板代码:
    IPage<UserBO> listUser(
        Page page, Map<String,Object> data );
*/