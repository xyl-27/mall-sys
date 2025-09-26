package com.gec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.domain.bo.CategoryBO;
import com.gec.domain.entity.Category;
import com.gec.domain.entity.Node;
import com.gec.domain.vo.CategoryBrandVO;
import com.gec.dao.CategoryMapper;
import com.gec.service.IBaseService;
import com.gec.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService, IBaseService {

    @Autowired
    private CategoryMapper categoryMapper;

    //实体扩展类属性拷贝
    @Override
    public Node copyObj(Node node) {
        Category C = (Category)node;
        String pIds = C.getPIds();
        String[] sp = pIds.split(",");
        int LEVEL = sp.length;
        C.setLevel(LEVEL);
        CategoryBO categoryBO = new CategoryBO(C);
        return categoryBO;
    }



    @Override
    public List<Node> listCategory() {
        // 1. 查询数据库所有分类
        List<Category> categories = categoryMapper.selectList(null);

        List<Node> nodes = convertNodeBO(categories);
        // 4. 调试打印树
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(nodes);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");

        return nodes;
    }





    @Override
    public String getPids(Integer id) {
        return categoryMapper.getPids(id);
    }

    //建立类别与品牌关联数据

    @Override
    public void associateBrand(CategoryBrandVO cbVO) {
        Integer categoryId = cbVO.getCategoryId();
        List<Integer> brandIds = cbVO.getDeleteIds();

        categoryMapper.removeAssociate(categoryId,brandIds);
        int cnt = categoryMapper.associateBrand(cbVO);
        if(cnt!=cbVO.getBrands().size()){
            throw new RuntimeException("关联品牌失败");
        }
    }

    @Override
    public Boolean removeCategoryById(Integer id) {

        return categoryMapper.deleteById(id);
    }

    @Override
    public Integer[] getPidsArr(Integer id){
        String ids = categoryMapper.getPids(id);
        ids = ids+","+id;
        String[] arr = ids.split(",");
        Integer[] i_arr = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            i_arr[i] = Integer.valueOf(arr[i]);
        }
        return i_arr;
    }
}
