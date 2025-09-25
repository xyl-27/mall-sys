package com.gec.domain.vo;

import com.gec.domain.entity.Brand;
import lombok.Data;

import java.util.List;

@Data
public class CategoryBrandVO {
    Integer categoryId;
    String  categoryName;

    //private Integer brandId;
    //private String brandName;
    private List<Brand> brands;
    private List<Integer> deleteIds;
}
