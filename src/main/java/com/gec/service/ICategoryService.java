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

    Boolean removeCategoryById(Integer id);
}
