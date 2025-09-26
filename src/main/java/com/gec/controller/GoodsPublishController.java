package com.gec.controller;

import com.gec.components.FileTemplate;
import com.gec.domain.vo.GoodsPublishVO;
import com.gec.service.IGoodsPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods/publish")
public class GoodsPublishController extends BaseController {

    @Autowired
    private IGoodsPublishService goodsPublishService;
    
    @Autowired
    private FileTemplate fileTemplate;

    /**
     * 商品上架全流程接口
     * @param goodsPublishVO 包含四个阶段数据的VO对象
     * @return 操作结果
     */
    @PostMapping("/fullProcess")
    public R publishFullProcess(@RequestBody GoodsPublishVO goodsPublishVO) {
        try {
            boolean result = goodsPublishService.publishGoods(goodsPublishVO);
            if (result) {
                return R.ok().put("message", "商品上架成功");
            } else {
                return R.err(null).put("message", "商品上架失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e).put("message", "商品上架过程中发生异常：" + e.getMessage());
        }
    }

    /**
     * 保存基础属性信息接口
     * @param goodsPublishVO 包含基础属性数据的VO对象
     * @return 操作结果和spuId
     */
    @PostMapping("/saveBasicInfo")
    public R saveBasicInfo(@RequestBody GoodsPublishVO goodsPublishVO) {
        try {
            Integer spuId = goodsPublishService.saveBasicInfo(goodsPublishVO);
            if (spuId != null && spuId > 0) {
                return R.ok().put("message", "基础信息保存成功").put("spuId", spuId);
            } else {
                return R.err(null).put("message", "基础信息保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e).put("message", "保存基础信息过程中发生异常：" + e.getMessage());
        }
    }

    /**
     * 保存规格参数信息接口
     * @param params 包含spuId和规格参数数据的请求参数
     * @return 操作结果
     */
    @PostMapping("/saveSpecInfo")
    public R saveSpecInfo(@RequestBody java.util.Map<String, Object> params) {
        try {
            Integer spuId = (Integer) params.get("spuId");
            GoodsPublishVO goodsPublishVO = new GoodsPublishVO();
            goodsPublishVO.setSpecInfo((java.util.List<java.util.Map<String, Object>>) params.get("specInfo"));
            
            boolean result = goodsPublishService.saveSpecInfo(goodsPublishVO, spuId);
            if (result) {
                return R.ok().put("message", "规格参数保存成功");
            } else {
                return R.err(null).put("message", "规格参数保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e).put("message", "保存规格参数过程中发生异常：" + e.getMessage());
        }
    }

    /**
     * 保存销售属性信息接口
     * @param params 包含spuId和销售属性数据的请求参数
     * @return 操作结果
     */
    @PostMapping("/saveSaleAttrInfo")
    public R saveSaleAttrInfo(@RequestBody java.util.Map<String, Object> params) {
        try {
            Integer spuId = (Integer) params.get("spuId");
            GoodsPublishVO goodsPublishVO = new GoodsPublishVO();
            goodsPublishVO.setSaleAttrInfo((java.util.List<java.util.Map<String, Object>>) params.get("saleAttrInfo"));
            
            boolean result = goodsPublishService.saveSaleAttrInfo(goodsPublishVO, spuId);
            if (result) {
                return R.ok().put("message", "销售属性保存成功");
            } else {
                return R.err(null).put("message", "销售属性保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e).put("message", "保存销售属性过程中发生异常：" + e.getMessage());
        }
    }

    /**
     * 保存SKU信息接口
     * @param params 包含spuId和SKU数据的请求参数
     * @return 操作结果
     */
    @PostMapping("/saveSkuInfo")
    public R saveSkuInfo(@RequestBody java.util.Map<String, Object> params) {
        try {
            Integer spuId = (Integer) params.get("spuId");
            GoodsPublishVO goodsPublishVO = new GoodsPublishVO();
            goodsPublishVO.setSkuInfo((java.util.Map<String, Object>) params.get("skuInfo"));
            
            boolean result = goodsPublishService.saveSkuInfo(goodsPublishVO, spuId);
            if (result) {
                return R.ok().put("message", "SKU信息保存成功");
            } else {
                return R.err(null).put("message", "SKU信息保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e).put("message", "保存SKU信息过程中发生异常：" + e.getMessage());
        }
    }

    @Override
    protected FileTemplate getFileTemplate() {
        return fileTemplate;
    }
}