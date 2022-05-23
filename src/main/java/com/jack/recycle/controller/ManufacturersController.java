package com.jack.recycle.controller;

import com.jack.recycle.model.*;
import com.jack.recycle.model.VO.StationAndUser;
import com.jack.recycle.model.VO.TradeAndGoods;
import com.jack.recycle.service.*;
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
    private GoodsService goodsService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeGoodsService tradeGoodsService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionGoodsService transactionGoodsService;

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
        //查询厂商列表
        List<Manufacturers> manufacturersList = manufacturersService.dirManufacturer(manufacturers);
        List<Trade> trades = tradeService.dirTrade(new Trade());
        List<String> factoryIds = new ArrayList<>();
        for (Trade trade : trades) {
            factoryIds.add(trade.getFactoryId());
        }
        for (Manufacturers mmm:manufacturersList) {
            if (factoryIds.contains(mmm.getUuid()))
                mmm.setIsVisible(true);
            else
                mmm.setIsVisible(false);
        }
        return new Result(Response.SC_OK,"OK",manufacturersList);
    }

    /*
     * 厂商与基站的交易
     */
    @PostMapping("/toPayFacture")
    public Result payStationManufacture(@RequestBody TradeAndGoods manufacture){
        Trade trade = manufacture.getTrade();
        trade.setUuid(UUID.randomUUID().toString());
        trade.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
        trade.setStationId(stationService.getStationDetailByUserId(UserUtils.getCurrUserInfo().getUuid()).getUuid());
        trade.setCreateTime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm"));
        tradeService.addTrade(trade);

        // 根据基站人员id获取他所拥有的交易信息合集
        Transaction transaction = new Transaction();
        transaction.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
        List<Transaction> transactions = transactionService.dirTransaction(transaction);
        // 把transactionId放到一个集合
        List<String> transactionIds = new ArrayList<>();
        for (Transaction tran : transactions) {
            transactionIds.add(tran.getUuid());
        }
        List<TransactionGoods> transactionGoodsList = transactionGoodsService.dirByTransactionIds(transactionIds);

        //获取与厂商交易的物品表
        List<TradeGoods> tradeGoods = manufacture.getTradeGoods();
        for (TradeGoods tg:tradeGoods) {
            if (tg.getWeight().equals(0.0))
                continue;
            tg.setUuid(UUID.randomUUID().toString());
            tg.setTradeId(trade.getUuid());
            tradeGoodsService.addTradeGoods(tg);
            // 根据物品名称去物品表里获取物品uuid，再根据物品uuid去transactionGoods表 改变isNull为0
            String goodsUuid = goodsService.getGoodsUuidByName(tg.getGoodsName());
            for (TransactionGoods transactionGoods : transactionGoodsList) {
                if(transactionGoods.getGoodsId().equals(goodsUuid)) {
                    transactionGoods.setIsNull(0);
                    transactionGoodsService.updTransactionGoods(transactionGoods);
                }
            }
        }
        return new Result(Response.SC_OK,"OK",null);
    }

    /*
       根据factoryId获取trade表工厂交易记录
     */
    @GetMapping("/getFactoryTrade")
    public Result getFactoryTrade(String factoryId){
        Trade trade = new Trade();
        trade.setFactoryId(factoryId);
        List<Trade> trades = tradeService.dirTrade(trade);
        for (Trade tr : trades) {
            StationAndUser stationDetail = stationService.getStationDetail(tr.getStationId());
            tr.setStationLegal(stationDetail.getUser().getRealName());
            tr.setStationId(stationDetail.getStation().getStationName());
        }
        return new Result(Response.SC_OK,"OK",trades);
    }

}
