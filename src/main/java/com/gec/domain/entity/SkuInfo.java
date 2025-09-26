package com.gec.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("tbl_sku_info")
@EqualsAndHashCode(callSuper = false)
public class SkuInfo extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer skuId;
    private Integer spuId;
    private String skuName;
    private String skuDesc;
    @TableField("catalog_id")
    private Integer categoryId;
    private Integer brandId;
    private String skuDefaultImg;
    private String skuTitle;
    private String skuSubtitle;
    private Double price;
    private Integer saleCount;

    @TableField(exist = false)
    private LocalDateTime localCreateDate;

    @TableField(exist = false)
    private LocalDateTime localUpdateDate;

}