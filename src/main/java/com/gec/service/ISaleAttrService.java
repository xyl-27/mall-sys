package com.gec.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.domain.bo.SaleAttrBO;
import com.gec.domain.entity.SaleAttr;

import java.util.Map;

public interface ISaleAttrService
        extends IService<SaleAttr> {

    IPage<SaleAttrBO> listSaleAttr(
            Page page, Map<String, Object> data);
    void addSaleAttr(SaleAttr saleAttr);
    void updateSaleAttr(SaleAttr saleAttr);
    void deleteSaleAttr(Integer id);

}

/*
    模板代码:
    IPage<UserBO> listUser(
        Page page, Map<String,Object> data );
*/