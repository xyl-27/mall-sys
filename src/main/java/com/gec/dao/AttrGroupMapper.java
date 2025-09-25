package com.gec.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.domain.bo.AttrGroupBO;
import com.gec.domain.entity.AttrGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface AttrGroupMapper
    extends BaseMapper<AttrGroup> {

    /**
     * 查询属性分组列表
     */
    Page<AttrGroupBO> getAttrGroupList(
            @Param("page") Page<AttrGroupBO> page,
            @Param("param") Map<String, Object> data);

}
