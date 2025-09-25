package com.gec.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.domain.entity.Category;
import com.gec.domain.entity.Node;
import com.gec.domain.vo.CategoryBrandVO;

import java.util.List;

public interface ICategoryService
        extends IService<Category> {

    List<Node> listCategory();
    Integer[] getPidsArr(Integer id);
    String getPids(Integer id);

    void associateBrand(CategoryBrandVO cbVO);
    
    // 检查类别是否可以删除（是否有关联的子类别或品牌或商品）
    boolean canDeleteCategory(Integer categoryId);
    
    // 删除类别
    void deleteCategory(Integer categoryId);
}
