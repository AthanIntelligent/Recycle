package com.jack.recycle.controller;

import com.jack.recycle.model.Goods;
import com.jack.recycle.model.GoodsOfStation;
import com.jack.recycle.model.GoodsType;
import com.jack.recycle.model.VO.GoodsTypeAndGoods;
import com.jack.recycle.model.VO.StationAndGoodsIds;
import com.jack.recycle.service.GoodsOfStationService;
import com.jack.recycle.service.GoodsService;
import com.jack.recycle.service.GoodsTypeService;
import com.jack.recycle.utils.Result;
import org.apache.catalina.connector.Response;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/goodsofstation")
public class GoodsOfStationController {
    @Autowired
    GoodsOfStationService goodsOfStationService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsTypeService goodsTypeService;
    /**
     * 添加基站所属物品类型
     */
    @PostMapping(value = "/addGoodsOfStation")
    public Result addGoodsOfStation(@RequestBody GoodsOfStation goodsOfStation){
        return new Result(Response.SC_OK,"success",goodsOfStationService.addGoodsOfStation(goodsOfStation));
    }

    /**
     * 根据基站法人id获取GoodsOfStation对象
     */
    @GetMapping(value = "/getGoodsOfStationByStationLegal")
    public Result getGoodsOfStationByStationLegal(String stationLegal){
        return new Result(Response.SC_OK,"success",goodsOfStationService.getGoodsOfStationByStationLegal(stationLegal));
    }

    /**
     * 根据基站id查询GoodsOfStation对象，返回StationAndGoods
     */
    @GetMapping(value = "/getGoodsOfStationByStationId/{stationId}")
    public Result getGoodsOfStationByStationId(@PathVariable("stationId") String stationId){
        GoodsOfStation res = goodsOfStationService.getGoodsOfStationByStationId(stationId);
        if (res == null) {
            return new Result(Response.SC_OK,"error","该基站没有经营物品");
        }
        String goodsIds = res.getGoodsIds();
        //基站人员选择经营的物品id
        List<String> goodsIdList = Arrays.asList(goodsIds.split(","));

        List<GoodsType> goodsTypesList = goodsTypeService.dirGoodsType();
        List<GoodsTypeAndGoods> result = new ArrayList<>();
        for (GoodsType goods:goodsTypesList){
            GoodsTypeAndGoods goodsTypeAndGoods = new GoodsTypeAndGoods();
            goodsTypeAndGoods.setGoodsType(goods.getGoodsType());
            Goods ggg = new Goods();
            ggg.setGoodsType(goods.getUuid());
            //查询出的全部物品
            List<Goods> goodsByTypeId = goodsService.dirGoods(ggg);
            for (Goods goo:goodsByTypeId) {
                if (goodsIdList.contains(goo.getUuid())) {
                    goo.setIsVisible(true);
                }
            }
            goodsTypeAndGoods.setGoodsList(goodsByTypeId);
            result.add(goodsTypeAndGoods);
        }

        return new Result(Response.SC_OK,"success",result);
    }
}
