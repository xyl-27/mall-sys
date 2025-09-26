package com.gec.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("tbl_product_attr_value")
@EqualsAndHashCode(callSuper = false)
public class ProductAttrValue extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer spuId;
    private Integer attrId;
    private String attrName;
    private String attrValue;
    private Integer attrSort;
    private Integer quickShow;

    @TableField(exist = false)
    private LocalDateTime createDateTime;

    @TableField(exist = false)
    private LocalDateTime updateDateTime;

}