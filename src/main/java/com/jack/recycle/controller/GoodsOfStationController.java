package com.jack.recycle.controller;

import com.jack.recycle.model.GoodsOfStation;
import com.jack.recycle.service.GoodsOfStationService;
import com.jack.recycle.utils.Result;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/typeofstation")
public class GoodsOfStationController {
    @Autowired
    GoodsOfStationService goodsOfStationService;
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
    @GetMapping(value = "/getGoodsOfStation")
    public Result getGoodsOfStation(String stationLegal){
        return new Result(Response.SC_OK,"success",goodsOfStationService.getGoodsOfStation(stationLegal));
    }
}
