package com.gec.controller;

import com.gec.domain.vo.GoodsPublishVO;
import com.gec.service.IGoodsPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/GoodsSup")
public class GoodsSpuController {

    @Autowired
    private IGoodsPublishService goodsPublishService;

    /**
     * 商品上架全流程接口
     * @param goodsPublishVO 包含四个阶段数据的VO对象
     * @return 操作结果
     */
    @PostMapping("/add")
    public R addGoodsSpu(@RequestBody GoodsPublishVO goodsPublishVO){
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

}