package com.gec.domain.vo;

import com.gec.domain.entity.GoodsSpu;
import com.gec.domain.entity.SpuBounds;
import lombok.Data;

@Data
public class GoodsSpuVO {
    private SpuBounds spuBounds;
    private GoodsSpu goodsSpu;

}
