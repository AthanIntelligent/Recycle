package com.jack.recycle.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.recycle.model.Goods;
import com.jack.recycle.model.Reservation;
import com.jack.recycle.model.Transaction;
import com.jack.recycle.model.TransactionGoods;
import com.jack.recycle.service.GoodsService;
import com.jack.recycle.service.ReservationService;
import com.jack.recycle.service.TransactionGoodsService;
import com.jack.recycle.service.TransactionService;
import com.jack.recycle.utils.DateUtils;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.StatusCode;
import com.jack.recycle.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/userandstationtransaction")
public class UserAndStationTransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionGoodsService transactionGoodsService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ReservationService reservationService;

    /*
    基站人员获取当月每天支出
     */
    @GetMapping(value = "/dirUandSTransaction")
    public Result dirUandSTransaction() {
        String formatDate = DateUtils.getFormatDate("yyyy-MM");
        Transaction transaction = new Transaction();
        transaction.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
//        transaction.setStationLegal("7b74e505-3924-4c03-86ed-acbbb6df4b92");
        transaction.setTransactionTime(formatDate);
        //获取当前月份该基站的所有交易记录
        List<Transaction> transactions = transactionService.dirUandSTransaction(transaction);
        Map<String, Double> map = new HashMap<>();
        for (Transaction tr:transactions) {
            String day = tr.getTransactionTime().substring(5, 10);
            if (!map.containsKey(day))
                map.put(day,tr.getAllMoney());
            else {
                Double mmm = map.get(day);
                mmm += tr.getAllMoney();
                map.replace(day,mmm);
            }
        }

        JSONArray result = new JSONArray();
        for(String key:map.keySet()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("day",key);
            jsonObject.put("money",map.get(key));
            result.add(jsonObject);
        }
        return new Result(StatusCode.OK, "OK", result);
    }

    /*
    基站人员获取支出物品金额占比，物品受欢迎程度
     */
    @GetMapping(value = "/dirGoodsPercent")
    public Result dirGoodsPercent() {
        Transaction transaction = new Transaction();
//        transaction.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
        transaction.setStationLegal("7b74e505-3924-4c03-86ed-acbbb6df4b92");
        List<Transaction> transactions = transactionService.dirUandSTransaction(transaction);
        List<String> tranIdsList = new ArrayList<>();
        List<Double> allMoneyList = new ArrayList<>();
        for (Transaction tr:transactions) {
            tranIdsList.add(tr.getUuid());
            allMoneyList.add(tr.getAllMoney());
        }
        Double allMoney = allMoneyList.stream().collect(Collectors.summingDouble(x -> x.doubleValue()));
        List<TransactionGoods> transactionGoods = transactionGoodsService.dirByTransactionIds(tranIdsList);
        Map<String, Double> map = new HashMap<>();
        for (TransactionGoods tg:transactionGoods) {
            if (!map.containsKey(tg.getGoodsId())) {
                map.put(tg.getGoodsId(),tg.getRmb());
            }else {
                Double money = map.get(tg.getGoodsId());
                money += tg.getRmb();
                map.replace(tg.getGoodsId(),money);
            }
        }

        //循环map把goodid转换成名字
        JSONArray result = new JSONArray();
        for(String key:map.keySet()) {
            String goodsName = goodsService.getGoods(key).getGoodsName();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",goodsName);
            jsonObject.put("value",map.get(key) / allMoney);
            result.add(jsonObject);
        }
        return new Result(StatusCode.OK, "OK", result);
    }

    /*
       基站人员获取预约状态占比
    */
    @GetMapping(value = "/dirReservationPercent")
    public Result dirReservationPercent() {
        Reservation reservation = new Reservation();
        reservation.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
//        reservation.setStationLegal("7b74e505-3924-4c03-86ed-acbbb6df4b92");
        List<Reservation> reservations = reservationService.dirReservation(reservation);
        Map<String, Integer> map = new HashMap<>();
        for (Reservation res:reservations) {
            if (!map.containsKey(res.getIsCome())) {
                map.put(res.getIsCome(),1);
            }else {
                map.replace(res.getIsCome(),map.get(res.getIsCome())+1);
            }
        }
//        DecimalFormat df = new DecimalFormat("0.00");
        JSONArray result = new JSONArray();
        for(String key:map.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",key);
//            jsonObject.put("rates",df.format((float)map.get(key) / reservations.size()));
            jsonObject.put("value",map.get(key));
            result.add(jsonObject);
        }
        return new Result(StatusCode.OK, "OK", result);
    }

}
