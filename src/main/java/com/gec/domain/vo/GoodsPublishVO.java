package com.gec.domain.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GoodsPublishVO {
    
    /**
     * 第一阶段：基础属性数据
     */
    private Map<String, Object> basicInfo;
    
    /**
     * 第二阶段：规格参数数据
     * 格式: [{"attrId": 6,"attrValue": "256g"},{"attrId": 7,"attrValue": "黑色"}]
     */
    private List<Map<String, Object>> specInfo;
    
    /**
     * 第三阶段：销售属性数据
     * 格式: [{"attrId": 29,"attrValue": "蓝色;黑色"},{"attrId": 32,"attrValue": "5000h;6000h"}]
     */
    private List<Map<String, Object>> saleAttrInfo;
    
    /**
     * 第四阶段：SKU信息数据
     */
    private Map<String, Object> skuInfo;
}