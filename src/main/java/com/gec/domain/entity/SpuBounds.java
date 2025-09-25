package com.gec.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@TableName(value="tbl_spu_bounds")
@EqualsAndHashCode(callSuper = false)
public class SpuBounds implements Serializable {
    @TableId(type=IdType.AUTO)
    private Integer id;
    private Integer spuId;
    private Integer growBounds;
    private Double buyBounds;
    private Integer work;
}
