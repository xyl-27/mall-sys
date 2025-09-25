package com.gec.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.dao.GoodsAttrMapper;
import com.gec.domain.bo.GoodsAttrBO;
import com.gec.domain.entity.GoodsAttr;
import com.gec.domain.vo.GoodsAttrVO;
import com.gec.service.IGoodsAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GoodsAttrServiceImpl
        extends ServiceImpl<GoodsAttrMapper, GoodsAttr>
        implements IGoodsAttrService {
    @Autowired
    private GoodsAttrMapper goodsAttrMapper;

    @Override
    public IPage<GoodsAttrBO> listGoodsAttr(
            Page page, Map<String, Object> data) {
        //{1}分页查询商品属性列表。
        Page<GoodsAttrBO> retPage = null;
        retPage = goodsAttrMapper.getGoodsAttrList(
                page, data);
        return retPage;
    }

    @Override
    public void addGoodsAttr(GoodsAttr goodsAttr) {
        //GoodsAttr entity = getEntity( attrVO );
        int cnt = goodsAttrMapper.insert( goodsAttr );
        //attrVO.setId( entity.getId() );
        //cnt += goodsAttrMapper.addGoodsAttrVal( attrVO );
        //cnt += goodsAttrMapper.setRelation(
        //        attrVO.getAttrGroupId(),
        //        entity.getId() );
        if( cnt != 1 ){
            throw new RuntimeException("新增规格属性失败。");
        }
    }

    @Override
    public void updateGoodsAttr(GoodsAttr goodsAttr) {
        //GoodsAttr entity = getEntity(goodsAttrVO);
        int cnt = goodsAttrMapper.updateById( goodsAttr );
        //goodsAttrMapper.removeAttrVal( entity.getId() );
        //goodsAttrMapper.addGoodsAttrVal( goodsAttrVO );
        if( cnt != 1 ){
            throw new RuntimeException("更新规格属性失败。");
        }
    }

    @Override
    public void deleteGoodsAttr(Integer id) {
        // 先删除属性值关联
        goodsAttrMapper.removeAttrVal(id);
        // 再删除属性本身
        int cnt = goodsAttrMapper.deleteById(id);
        if( cnt != 1 ){
            throw new RuntimeException("删除规格属性失败。");
        }
    }

    private GoodsAttr getEntity(GoodsAttrVO attrVO) {
        GoodsAttr attr = new GoodsAttr();
        attr.setId( attrVO.getId() );
        attr.setAttrName( attrVO.getAttrName() );
        attr.setCategoryId( attrVO.getCategoryId() );
        attr.setAttrType( attrVO.getAttrType() );
        attr.setEnable( attrVO.getEnable() );
        attr.setSearchEnable( attrVO.getSearchEnable() );
        return attr;
    }
}
