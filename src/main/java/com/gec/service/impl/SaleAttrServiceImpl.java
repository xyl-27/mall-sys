package com.gec.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.dao.SaleAttrMapper;
import com.gec.domain.bo.SaleAttrBO;
import com.gec.domain.entity.SaleAttr;
import com.gec.service.ISaleAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SaleAttrServiceImpl
        extends ServiceImpl<SaleAttrMapper, SaleAttr>
        implements ISaleAttrService {
    @Autowired
    private SaleAttrMapper saleAttrMapper;

    @Override
    public IPage<SaleAttrBO> listSaleAttr(
            Page page, Map<String, Object> data) {
        //{1}分页查询销售属性列表。
        Page<SaleAttrBO> retPage = null;
        retPage = saleAttrMapper.getSaleAttrList(
                page, data);
        return retPage;
    }

    @Override
    public void addSaleAttr(SaleAttr saleAttr) {
        int cnt = saleAttrMapper.insert( saleAttr );
        if( cnt != 1 ){
            throw new RuntimeException("新增销售属性失败。");
        }
    }

    @Override
    public void updateSaleAttr(SaleAttr saleAttr) {
        int cnt = saleAttrMapper.updateById( saleAttr );
        if( cnt != 1 ){
            throw new RuntimeException("更新销售属性失败。");
        }
    }

    @Override
    public void deleteSaleAttr(Integer id) {
        // 先删除属性值关联
        saleAttrMapper.removeAttrVal(id);
        // 再删除属性本身
        int cnt = saleAttrMapper.deleteById(id);
        if( cnt != 1 ){
            throw new RuntimeException("删除销售属性失败。");
        }
    }
}