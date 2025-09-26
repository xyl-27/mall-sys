package com.gec.service;

import com.gec.domain.vo.GoodsPublishVO;

public interface IGoodsPublishService {

    /**
     * 商品上架全流程
     * @param goodsPublishVO 包含四个阶段数据的VO对象
     * @return 操作结果
     */
    boolean publishGoods(GoodsPublishVO goodsPublishVO);

    /**
     * 保存基础属性信息
     * @param goodsPublishVO 包含基础属性数据的VO对象
     * @return 保存的spuId
     */
    Integer saveBasicInfo(GoodsPublishVO goodsPublishVO);

    /**
     * 保存规格参数信息
     * @param goodsPublishVO 包含规格参数数据的VO对象
     * @param spuId 商品spuId
     * @return 操作结果
     */
    boolean saveSpecInfo(GoodsPublishVO goodsPublishVO, Integer spuId);

    /**
     * 保存销售属性信息
     * @param goodsPublishVO 包含销售属性数据的VO对象
     * @param spuId 商品spuId
     * @return 操作结果
     */
    boolean saveSaleAttrInfo(GoodsPublishVO goodsPublishVO, Integer spuId);

    /**
     * 生成并保存SKU信息
     * @param goodsPublishVO 包含SKU数据的VO对象
     * @param spuId 商品spuId
     * @return 操作结果
     */
    boolean saveSkuInfo(GoodsPublishVO goodsPublishVO, Integer spuId);
}