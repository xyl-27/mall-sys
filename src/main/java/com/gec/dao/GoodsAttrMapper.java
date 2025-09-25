package com.gec.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.domain.bo.GoodsAttrBO;
import com.gec.domain.entity.GoodsAttr;
import com.gec.domain.vo.GoodsAttrVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsAttrMapper extends BaseMapper<GoodsAttr> {

    /**
     * 查询商品属性列表
     */
    Page<GoodsAttrBO> getGoodsAttrList(
            @Param("page") Page<GoodsAttrBO> page,
            @Param("param") Map<String, Object> data);

    /**
     * 根据属性ID获取属性值列表
     */
    List<GoodsAttr> getAttrValByAttrId(@Param("ids") List<Integer> ids);

    /**
     * 添加商品属性值
     */
    int addGoodsAttrVal(GoodsAttrVO attrVO);

    /**
     * 设置属性与属性组的关联关系
     */
    int setRelation(@Param("attrGroupId") Integer attrGroupId, @Param("attrId") Integer attrId);

    /**
     * 移除属性值
     */
    void removeAttrVal(Integer id);

}