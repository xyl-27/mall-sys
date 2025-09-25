package com.gec.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.domain.bo.SaleAttrBO;
import com.gec.domain.entity.SaleAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SaleAttrMapper extends BaseMapper<SaleAttr> {

    /**
     * 查询销售属性列表
     */
    Page<SaleAttrBO> getSaleAttrList(
            @Param("page") Page<SaleAttrBO> page,
            @Param("param") Map<String, Object> data);

    /**
     * 根据属性ID获取属性值列表
     */
    List<SaleAttr> getAttrValByAttrId(@Param("ids") List<Integer> ids);

    /**
     * 移除属性值
     */
    void removeAttrVal(Integer id);

}