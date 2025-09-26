package com.gec.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.domain.bo.GoodsAttrBO;
import com.gec.domain.entity.GoodsAttrGroup;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface IAttrGroupService
        extends IService<GoodsAttrGroup> {

    IPage listAttrGroup(@Param("page") Page<GoodsAttrBO> page, @Param("param") Map<String, Object> data);

    void addAttrGroup(GoodsAttrGroup attrGroup);

    void updateAttrGroup(GoodsAttrGroup attrGroup);

    void deleteById(Integer id);
}
