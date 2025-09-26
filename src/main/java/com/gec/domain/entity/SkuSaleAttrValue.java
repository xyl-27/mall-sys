package com.gec.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("tbl_sku_sale_attr_value")
@EqualsAndHashCode(callSuper = false)
public class SkuSaleAttrValue extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer skuId;
    private Integer spuId;
    private Integer attrId;
    @TableField(exist = false)
    private String attrName;
    private String attrValue;
    @TableField(exist = false)
    private Integer attrSort;

    @TableField(exist = false)
    private LocalDateTime createDateTime;

    @TableField(exist = false)
    private LocalDateTime updateDateTime;

}