package com.jack.recycle.controller;

import com.jack.recycle.model.Manufacturers;
import com.jack.recycle.model.Trade;
import com.jack.recycle.model.TradeGoods;
import com.jack.recycle.model.VO.TradeAndGoods;
import com.jack.recycle.service.ManufacturersService;
import com.jack.recycle.service.StationService;
import com.jack.recycle.service.TradeGoodsService;
import com.jack.recycle.service.TradeService;
import com.jack.recycle.utils.DateUtils;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.UserUtils;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/facture")
public class ManufacturersController {
    @Autowired
    ManufacturersService manufacturersService;

    @Autowired
    private StationService stationService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeGoodsService tradeGoodsService;

    @PostMapping("/addManufacturers")
    public Result addManufacturers(@RequestBody Manufacturers manufacturers){
        return new Result(Response.SC_OK,"OK",manufacturersService.addManufacturer(manufacturers));
    }

    @GetMapping("/delManufacturers")
    public Result delManufacturers(String uuid){
        return new Result(Response.SC_OK,"OK",manufacturersService.removeManufacturer(uuid));
    }

    @GetMapping("/getManufacturers")
    public Result getManufacturers(String uuid){
        return new Result(Response.SC_OK,"OK",manufacturersService.getManufacturer(uuid));
    }

    @PostMapping("/dirManufacturers")
    public Result dirManufacturers(@RequestBody Manufacturers manufacturers){
        return new Result(Response.SC_OK,"OK",manufacturersService.dirManufacturer(manufacturers));
    }

    @PostMapping("/toPayFacture")
    public Result payStationManufacture(@RequestBody TradeAndGoods manufacture){
        Trade trade = manufacture.getTrade();
        trade.setUuid(UUID.randomUUID().toString());
        trade.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
        trade.setStationId(stationService.getStationDetailByUserId(UserUtils.getCurrUserInfo().getUuid()).getUuid());
        trade.setCreateTime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm"));
        tradeService.addTrade(trade);

        List<TradeGoods> tradeGoods = manufacture.getTradeGoods();
        for (TradeGoods tg:tradeGoods) {
            if (tg.getWeight().equals(0.0))
                continue;
            tg.setUuid(UUID.randomUUID().toString());
            tg.setTradeId(trade.getUuid());
            tradeGoodsService.addTradeGoods(tg);
        }
        return new Result(Response.SC_OK,"OK",null);
    }


}
