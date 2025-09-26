package com.gec.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.domain.bo.GoodsAttrBO;
import com.gec.domain.entity.GoodsAttr;
import com.gec.domain.entity.GoodsAttrValue;
import com.gec.domain.vo.GoodsAttrVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsAttrMapper extends BaseMapper<GoodsAttr> {

    Page<GoodsAttrBO> getGoodsAttrList(
            @Param("page") Page<GoodsAttrBO> page,
            @Param("param") Map<String, Object> data);

    List<GoodsAttrValue> getAttrValByAttrId(
            @Param("ids") List<Integer> ids);

    int addGoodsAttrVal(GoodsAttrVO attrVO);

    int removeGoodsAttrVal(
            @Param("attrId")Integer attrId);

    int setRelation(
            @Param("attrGroupId") Integer attrGroupId,
            @Param("attrId") Integer attrId);

    int addGroupAttrRelation(
            @Param("attrGroupId") Integer attrGroupId,
            @Param("attrId") Integer attrId);

    int removeGroupAttrRelation(
            @Param("attrId") Integer attrId);
}