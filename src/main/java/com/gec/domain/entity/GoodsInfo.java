package com.gec.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("tbl_goods_info")
@EqualsAndHashCode(callSuper = false)
public class GoodsInfo extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String spuName;
    private String spuDescription;
    private Integer categoryId;
    private Integer brandId;
    private Double weight;
    private Integer publishStatus;
    private String createTime;
    private String updateTime;

    @TableField(exist = false)
    private LocalDateTime localCreateDate;

    @TableField(exist = false)
    private LocalDateTime localUpdateDate;

}