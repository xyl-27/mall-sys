package com.gec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gec.domain.entity.*;
import com.gec.domain.vo.GoodsPublishVO;
import com.gec.dao.*;
import com.gec.service.IGoodsPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GoodsPublishServiceImpl implements IGoodsPublishService {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsImagesMapper goodsImagesMapper;

    @Autowired
    private ProductAttrValueMapper productAttrValueMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImagesMapper skuImagesMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private GoodsAttrMapper goodsAttrMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publishGoods(GoodsPublishVO goodsPublishVO) {
        try {
            System.out.println("开始发布商品...");
            // 1. 保存基础信息
            Integer spuId = saveBasicInfo(goodsPublishVO);
            System.out.println("保存基础信息成功，spuId: " + spuId);
            if (spuId == null || spuId <= 0) {
                System.out.println("spuId无效，返回false");
                return false;
            }

            // 2. 保存规格参数信息
            System.out.println("准备保存规格参数信息...");
            boolean specResult = saveSpecInfo(goodsPublishVO, spuId);
            System.out.println("保存规格参数信息结果: " + specResult);
            if (!specResult) {
                System.out.println("保存规格参数信息失败，返回false");
                return false;
            }

            // 3. 保存销售属性信息
            boolean saleAttrResult = saveSaleAttrInfo(goodsPublishVO, spuId);
            if (!saleAttrResult) {
                return false;
            }

            // 4. 生成并保存SKU信息
            boolean skuResult = saveSkuInfo(goodsPublishVO, spuId);
            if (!skuResult) {
                return false;
            }

            // 5. 设置商品状态为已发布
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setId(spuId);
            goodsInfo.setPublishStatus(1);
            goodsInfoMapper.updateById(goodsInfo);

            System.out.println("商品发布成功");
            return true;
        } catch (Exception e) {
            System.out.println("商品发布过程中发生异常: " + e.getMessage());
            e.printStackTrace();
            throw e; // 重新抛出异常以触发事务回滚
        }
    }

    @Override
    public Integer saveBasicInfo(GoodsPublishVO goodsPublishVO) {
        Map<String, Object> basicInfo = goodsPublishVO.getBasicInfo();
        if (basicInfo == null) {
            return null;
        }

        // 保存商品基本信息
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setSpuName((String) basicInfo.get("spuName"));
        goodsInfo.setSpuDescription((String) basicInfo.get("spuDescription"));
        goodsInfo.setCategoryId((Integer) basicInfo.get("categoryId"));
        goodsInfo.setBrandId((Integer) basicInfo.get("brandId"));
        goodsInfo.setWeight((Double) basicInfo.get("weight"));
        goodsInfo.setPublishStatus(0); // 初始状态为未发布
        
        // 设置时间
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        goodsInfo.setCreateTime(now);
        goodsInfo.setUpdateTime(now);

        // 插入并获取自增ID
        goodsInfoMapper.insert(goodsInfo);
        Integer spuId = goodsInfo.getId();

        // 保存商品图片信息
        List<String> imgUrls = (List<String>) basicInfo.get("imgUrls");
        if (imgUrls != null && !imgUrls.isEmpty()) {
            for (int i = 0; i < imgUrls.size(); i++) {
                GoodsImages goodsImages = new GoodsImages();
                goodsImages.setSpuId(spuId);
                goodsImages.setImageName(String.format("%03d", i + 1)); // 001, 002...
                goodsImages.setImgUrl(imgUrls.get(i));
                goodsImages.setImgSort(i);
                goodsImages.setDefaultImg(i == 0 ? 1 : 0); // 第一张设为默认图
                goodsImagesMapper.insert(goodsImages);
            }
        }

        return spuId;
    }

    @Override
    public boolean saveSpecInfo(GoodsPublishVO goodsPublishVO, Integer spuId) {
        System.out.println("开始保存规格参数信息，spuId: " + spuId);
        
        List<Map<String, Object>> specInfo = goodsPublishVO.getSpecInfo();
        System.out.println("///////////////////////////////////");
        System.out.println("specInfo: " + specInfo);
        
        if (specInfo == null || spuId == null || specInfo.isEmpty()) {
            System.out.println("specInfo为null、空列表或spuId为null，返回false");
            return false;
        }

        // 保存规格参数值
        boolean allInsertSuccess = true;
        for (int i = 0; i < specInfo.size(); i++) {
            Map<String, Object> specItem = specInfo.get(i);
            Integer attrId = (Integer) specItem.get("attrId");
            String attrValue = (String) specItem.get("attrValue");
            
            System.out.println("处理规格参数项：attrId=" + attrId + ", attrValue=" + attrValue);
            
            // 根据attrId查询GoodsAttr信息
            GoodsAttr goodsAttr = goodsAttrMapper.selectById(attrId);
            if (goodsAttr == null) {
                System.out.println("未找到ID为" + attrId + "的属性信息，跳过该项");
                continue;
            }
            
            System.out.println("获取到属性名称：" + goodsAttr.getAttrName());
            
            ProductAttrValue productAttrValue = new ProductAttrValue();
            // 关联 tbl_goods_info 表的 id 字段与 tbl_product_attr_value 表的 spu_id 字段
            productAttrValue.setSpuId(spuId);
            // 将 tbl_goods_attr 表的 id 字段值存入 tbl_product_attr_value 表的 attr_id 字段
            productAttrValue.setAttrId(attrId);
            // 将 tbl_goods_attr 表的 attr_name 字段值存入 tbl_product_attr_value 表
            productAttrValue.setAttrName(goodsAttr.getAttrName());
            
            // 设置属性值
            productAttrValue.setAttrValue(attrValue != null ? attrValue : "");
            
            // attr_sort 的处理方式与图片处理时保持一致，使用循环索引
            productAttrValue.setAttrSort(i);
            productAttrValue.setQuickShow(1); // 默认快速展示
            
            System.out.println("准备插入ProductAttrValue对象：" + productAttrValue);
            try {
                int result = productAttrValueMapper.insert(productAttrValue);
                System.out.println("插入结果：" + result + ", 影响行数：" + result);
                if (result <= 0) {
                    System.out.println("插入失败，影响行数为0");
                    allInsertSuccess = false;
                }
            } catch (Exception e) {
                System.out.println("插入异常：" + e.getMessage());
                e.printStackTrace();
                allInsertSuccess = false;
            }
        }

        System.out.println("规格参数信息保存完成，全部插入成功：" + allInsertSuccess);
        return allInsertSuccess;
    }

    @Override
    public boolean saveSaleAttrInfo(GoodsPublishVO goodsPublishVO, Integer spuId) {
        List<Map<String, Object>> saleAttrInfo = goodsPublishVO.getSaleAttrInfo();
        if (saleAttrInfo == null || spuId == null || saleAttrInfo.isEmpty()) {
            System.out.println("saleAttrInfo为null、空列表或spuId为null，返回false");
            return false;
        }

        System.out.println("///////////////////////////////////");
        System.out.println("saleAttrInfo: " + saleAttrInfo);

        // 保存销售属性值
        boolean allInsertSuccess = true;
        for (Map<String, Object> saleAttrItem : saleAttrInfo) {
            Integer attrId = (Integer) saleAttrItem.get("attrId");
            String attrValuesStr = (String) saleAttrItem.get("attrValue");
            
            System.out.println("处理销售属性项：attrId=" + attrId + ", attrValuesStr=" + attrValuesStr);
            
            // 根据attrId查询GoodsAttr信息
            GoodsAttr goodsAttr = goodsAttrMapper.selectById(attrId);
            if (goodsAttr == null) {
                System.out.println("未找到ID为" + attrId + "的销售属性信息，跳过该项");
                continue;
            }
            
            System.out.println("获取到销售属性名称：" + goodsAttr.getAttrName());
            
            // 处理分号分隔的属性值
            if (attrValuesStr != null && !attrValuesStr.isEmpty()) {
                String[] attrValues = attrValuesStr.split(";\\s*");
                for (String attrValue : attrValues) {
                    SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
                    skuSaleAttrValue.setSkuId(0); // 暂时设置为0，后面会更新
                    skuSaleAttrValue.setSpuId(spuId); // 设置spuId
                    skuSaleAttrValue.setAttrId(attrId);
                    skuSaleAttrValue.setAttrValue(attrValue);
                    
                    System.out.println("准备插入SkuSaleAttrValue对象：" + skuSaleAttrValue);
                    try {
                        int result = skuSaleAttrValueMapper.insert(skuSaleAttrValue);
                        System.out.println("插入结果：" + result + ", 影响行数：" + result);
                        if (result <= 0) {
                            System.out.println("插入失败，影响行数为0");
                            allInsertSuccess = false;
                        }
                    } catch (Exception e) {
                        System.out.println("插入异常：" + e.getMessage());
                        e.printStackTrace();
                        allInsertSuccess = false;
                    }
                }
            }
        }

        System.out.println("销售属性信息保存完成，全部插入成功：" + allInsertSuccess);
        return allInsertSuccess;
    }

    @Override
    public boolean saveSkuInfo(GoodsPublishVO goodsPublishVO, Integer spuId) {
        Map<String, Object> skuInfoMap = goodsPublishVO.getSkuInfo();
        if (skuInfoMap == null || spuId == null) {
            return false;
        }

        // 获取商品基本信息
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(spuId);
        if (goodsInfo == null) {
            return false;
        }

        // 获取默认图片
        QueryWrapper<GoodsImages> imgQueryWrapper = new QueryWrapper<>();
        imgQueryWrapper.eq("spu_id", spuId);
        imgQueryWrapper.eq("default_img", 1);
        GoodsImages defaultImage = goodsImagesMapper.selectOne(imgQueryWrapper);
        String defaultImgUrl = defaultImage != null ? defaultImage.getImgUrl() : "";

        // 保存SKU信息
        List<Map<String, Object>> skuList = (List<Map<String, Object>>) skuInfoMap.get("skuList");
        if (skuList != null && !skuList.isEmpty()) {
            for (Map<String, Object> skuMap : skuList) {
                SkuInfo skuInfo = new SkuInfo();
                skuInfo.setSpuId(spuId);
                skuInfo.setSkuName((String) skuMap.get("skuName"));
                skuInfo.setSkuDesc((String) skuMap.get("skuDesc"));
                skuInfo.setCategoryId(goodsInfo.getCategoryId());
                skuInfo.setBrandId(goodsInfo.getBrandId());
                skuInfo.setSkuDefaultImg(defaultImgUrl);
                skuInfo.setSkuTitle((String) skuMap.get("skuTitle"));
                skuInfo.setSkuSubtitle((String) skuMap.get("skuSubtitle"));
                skuInfo.setPrice(Double.valueOf(skuMap.get("price").toString()));
                skuInfo.setSaleCount(0); // 初始销量为0
                
                // 插入SKU并获取自增ID
                skuInfoMapper.insert(skuInfo);
                Integer skuId = skuInfo.getSkuId();

                // 保存SKU图片
                List<String> skuImgUrls = (List<String>) skuMap.get("imgUrls");
                if (skuImgUrls != null && !skuImgUrls.isEmpty()) {
                    for (int i = 0; i < skuImgUrls.size(); i++) {
                        SkuImages skuImages = new SkuImages();
                        skuImages.setSkuId(skuId);
                        skuImages.setImgUrl(skuImgUrls.get(i));
                        skuImages.setImgSort(i);
                        skuImages.setDefaultImg(i == 0 ? 1 : 0);
                        skuImagesMapper.insert(skuImages);
                    }
                }

                // 保存SKU销售属性
                List<Map<String, Object>> saleAttrs = (List<Map<String, Object>>) skuMap.get("saleAttrs");
                if (saleAttrs != null && !saleAttrs.isEmpty()) {
                    for (Map<String, Object> saleAttr : saleAttrs) {
                        SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
                        skuSaleAttrValue.setSkuId(skuId);
                        skuSaleAttrValue.setSpuId(spuId); // 设置spuId
                        skuSaleAttrValue.setAttrId((Integer) saleAttr.get("attrId"));
                        skuSaleAttrValue.setAttrValue((String) saleAttr.get("attrValue"));
                        skuSaleAttrValueMapper.insert(skuSaleAttrValue);
                    }
                }
            }
        }

        return true;
    }
}