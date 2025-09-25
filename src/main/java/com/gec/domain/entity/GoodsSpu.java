package com.gec.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tbl_spu_temp")  //临时测试
public class GoodsSpu {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String goodsName;
    private String goodsDetails;
    private String categoryId;
    private String brandId;
    private String weight;
    private String createDate;
    private String updateDate;
}

