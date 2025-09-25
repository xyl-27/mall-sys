package com.gec.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.domain.bo.AttrGroupBO;
import com.gec.domain.entity.AttrGroup;

import java.util.Map;

public interface IAttrGroupService
        extends IService<AttrGroup> {

    IPage<AttrGroupBO> listAttrGroup(Page page, Map<String, Object> data);

    void addAttrGroup(AttrGroup attrGroup);
    
    void updateAttrGroup(AttrGroup attrGroup);
    
    void deleteAttrGroup(Integer id);
}
