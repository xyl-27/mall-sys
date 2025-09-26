package com.gec.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.domain.bo.GoodsAttrBO;
import com.gec.domain.entity.GoodsAttr;
import com.gec.domain.vo.GoodsAttrVO;
import com.gec.dao.GoodsAttrMapper;
import com.gec.service.IGoodsAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class GoodsAttrServiceImpl extends ServiceImpl<GoodsAttrMapper, GoodsAttr> implements IGoodsAttrService {

    @Autowired
    GoodsAttrMapper goodsAttrMapper;


    @Override
    public IPage<GoodsAttrBO> listGoodsAttr(Page page, Map<String, Object> data) {
        // 分页查询商品属性列表
        Page<GoodsAttrBO> retPage = goodsAttrMapper.getGoodsAttrList(page, data);
        return retPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addGoodsAttr(GoodsAttrVO attrVO) {
        GoodsAttr entity = getEntity( attrVO );
        int cnt = goodsAttrMapper.insert( entity );
        attrVO.setId( entity.getId() );
        goodsAttrMapper.addGroupAttrRelation(
                attrVO.getAttrGroupId(),
                entity.getId() );
        if (cnt != 1) {
            throw new RuntimeException("新增属性失败。");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateGoodsAttr(GoodsAttrVO attrVO) {
        GoodsAttr entity = getEntity(attrVO);
        Integer attrId = entity.getId();
        int cnt = goodsAttrMapper.updateById( entity );
        goodsAttrMapper.removeGroupAttrRelation( attrId );
        goodsAttrMapper.addGroupAttrRelation(
                attrVO.getAttrGroupId(),
                entity.getId() );
        if( cnt!=1 ){
            throw new RuntimeException("更新规格属性失败。");
        }
    }

    @Override
    public void deleteGoodsAttr(Integer id) {
        int cnt = goodsAttrMapper.deleteById(id);
        goodsAttrMapper.removeGroupAttrRelation( id );
        if (cnt != 1) {
            throw new RuntimeException("删除属性失败。");
        }
    }



    private GoodsAttr getEntity(GoodsAttrVO attrVO) {
        GoodsAttr attr = new GoodsAttr();
        attr.setId(attrVO.getId());
        attr.setAttrName(attrVO.getAttrName());
        attr.setCategoryId(attrVO.getCategoryId());
        attr.setAttrType(attrVO.getAttrType());
        attr.setEnable(attrVO.getEnable());
        attr.setSearchEnable(attrVO.getSearchEnable());
        return attr;
    }

}