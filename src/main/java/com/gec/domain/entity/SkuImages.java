package com.gec.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("tbl_sku_images")
@EqualsAndHashCode(callSuper = false)
public class SkuImages extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer skuId;
    private String imgUrl;
    private Integer imgSort;
    private Integer defaultImg;

    @TableField(exist = false)
    private LocalDateTime localCreateDate;

    @TableField(exist = false)
    private LocalDateTime localUpdateDate;

}