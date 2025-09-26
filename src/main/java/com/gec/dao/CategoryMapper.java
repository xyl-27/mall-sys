package com.gec.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.domain.entity.Category;
import com.gec.domain.vo.CategoryBrandVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface CategoryMapper
        extends BaseMapper<Category> {

    /*-- 1.获取父 ID 序列 --*/
    @Select("select p_ids from tbl_goods_category where id = #{id}")
    String getPids(Integer id);

    int removeAssociate(@Param("categoryId") Integer categoryId,
                        @Param("brandIds") List<Integer> brandIds);
    int associateBrand(@Param("cbVO") CategoryBrandVO cbVO);

    Boolean deleteById(@Param("id")Integer id);

}
