package com.gec.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.domain.entity.Brand;
import com.gec.domain.vo.BrandCategoryVO;
import com.gec.domain.vo.BrandVO;

import java.util.Map;

public interface IBrandService extends IService<Brand> {
    IPage<Brand> listBrand(Page page, Map data);
    void associateCategory(BrandCategoryVO bcVO);
    IPage<BrandVO> getListByCategory(
            Page page, Map data);

    void addBrand(Brand brand);

    void updateBrand(Brand brand);
    
    // 删除品牌
    void deleteBrand(Integer id);
    
    // 检查品牌是否有关联数据
    boolean hasAssociatedData(Integer brandId);
}


