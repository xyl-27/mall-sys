package com.gec.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.domain.bo.AttrGroupBO;
import com.gec.domain.bo.GoodsAttrBO;
import com.gec.domain.entity.Category;
import com.gec.domain.entity.GoodsAttrGroup;
import com.gec.dao.AttrGroupMapper;
import com.gec.dao.CategoryMapper;
import com.gec.service.IAttrGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, GoodsAttrGroup> implements IAttrGroupService {

    @Autowired
    private AttrGroupMapper attrGroupMapper;
    @Override
    public IPage listAttrGroup(Page<GoodsAttrBO> page, Map<String, Object> data) {
        Page<AttrGroupBO> retPage = attrGroupMapper.getAttrGroupList(page, data);
        return retPage;
    }

    @Override
    public void addAttrGroup(GoodsAttrGroup attrGroup) {
        int cnt = attrGroupMapper.insert(attrGroup);
        if( cnt!=1 ){
            throw new RuntimeException("添加属性分组失败。");
        }
    }

    @Override
    public void updateAttrGroup(GoodsAttrGroup attrGroup) {
        UpdateWrapper<GoodsAttrGroup> UW = new UpdateWrapper<>();
        UW.eq("id", attrGroup.getId());
        int cnt = attrGroupMapper.update(attrGroup,UW);
        if( cnt!=1 ){
            throw new RuntimeException("更新属性分组失败。");
        }
    }

    @Override
    public void deleteById(Integer id) {
        int cnt = attrGroupMapper.deleteById(id);
        if (cnt != 1) {
            throw new RuntimeException("删除属性分组失败。");
        }
    }
}
