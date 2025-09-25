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

    /*
     * 1.获取上代 ID 序列
     *   上代 ID 序列: 上帝(0),曾祖父,祖父,父亲
     */
    @Select("SELECT p_ids from tbl_goods_category " +
            " WHERE id=#{id}")
    String getPids(Integer id);

    int removeAssociate(
        @Param("categoryId") Integer categoryId,
        @Param("brandIds") List<Integer> brandIds
    );

    int associateBrand(
        @Param("cbVO") CategoryBrandVO cbVO);
    
    // 检查类别是否有子类别
    @Select("SELECT COUNT(*) FROM tbl_goods_category WHERE parent_id = #{categoryId}")
    int hasSubCategories(@Param("categoryId") Integer categoryId);
    
    // 检查类别是否有关联的品牌
    @Select("SELECT COUNT(*) FROM tbl_brand_category WHERE category_id = #{categoryId}")
    int hasAssociatedBrands(@Param("categoryId") Integer categoryId);
    
    // 检查类别是否有商品
    @Select("SELECT COUNT(*) FROM tbl_goods WHERE category_id = #{categoryId}")
    int hasProducts(@Param("categoryId") Integer categoryId);
    
    //List<Category> getList();
}
