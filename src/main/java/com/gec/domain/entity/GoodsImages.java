package com.gec.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("tbl_goods_images")
@EqualsAndHashCode(callSuper = false)
public class GoodsImages extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer spuId;
    @TableField("img_name")
    private String imageName;
    private String imgUrl;
    private Integer imgSort;
    private Integer defaultImg;
    
    // 排除父类中的日期字段，因为数据库表中不存在这些字段
    @TableField(exist = false)
    private LocalDateTime createDateTime;

    @TableField(exist = false)
    private LocalDateTime updateDateTime;



}