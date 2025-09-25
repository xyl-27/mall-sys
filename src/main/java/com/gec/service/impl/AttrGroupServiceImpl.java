package com.gec.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.dao.AttrGroupMapper;
import com.gec.domain.bo.AttrGroupBO;
import com.gec.domain.entity.AttrGroup;
import com.gec.service.IAttrGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AttrGroupServiceImpl
    extends ServiceImpl<AttrGroupMapper, AttrGroup>
    implements IAttrGroupService {
    @Autowired
    private AttrGroupMapper attrGroupMapper;
    @Override
    public IPage<AttrGroupBO> listAttrGroup(Page page, Map<String, Object> data) {
        // 分页查询属性分组列表
        Page<AttrGroupBO> retPage = null;
        retPage = attrGroupMapper.getAttrGroupList(page, data);
        return retPage;
    }

    @Override
    public void addAttrGroup(AttrGroup attrGroup) {
        int cnt = attrGroupMapper.insert(attrGroup);
        if( cnt!=1 ){
            throw new RuntimeException("添加属性分组失败。");
        }
    }

    @Override
    public void updateAttrGroup(AttrGroup attrGroup) {
        int cnt = attrGroupMapper.updateById(attrGroup);
        if( cnt!=1 ){
            throw new RuntimeException("更新属性分组失败。");
        }
    }
    
    @Override
    public void deleteAttrGroup(Integer id) {
        int cnt = attrGroupMapper.deleteById(id);
        if( cnt!=1 ){
            throw new RuntimeException("删除属性分组失败。");
        }
    }

}
