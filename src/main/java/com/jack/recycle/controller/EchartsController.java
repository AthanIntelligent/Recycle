package com.jack.recycle.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.recycle.model.*;
import com.jack.recycle.model.VO.StationTop;
import com.jack.recycle.service.*;
import com.jack.recycle.utils.Result;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.pkcs11.Secmod;

import java.util.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    private StationService stationService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private TransactionGoodsService transactionGoodsService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsTypeService goodsTypeService;
    @GetMapping(value = "/getStationDisplayData")
    public Result getStationDisplayData(){
        List<JSONArray> list = new ArrayList<>();
        List<Station> stations = stationService.dirStation(null);
        Map<String,Integer> map = new HashMap<>();
        for(int i=0;i<stations.size();i++){
            int left=0;
            int right=0;
            Station station = stations.get(i);
            char[] chars = station.getStationAddress().toCharArray();
            for(int j=0;j<chars.length;j++){
                if(chars[j]=='省'){
                    left=j+1;
                }else if(chars[j]=='市'){
                    right=j;
                    break;
                }
            }
            String city = station.getStationAddress().substring(left,right);
            if(!map.containsKey(city))
            {
                map.put(city,1);
            }else {
                map.put(city,map.get(city)+1);
            }
        }
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
        while(iterator.hasNext()){
            JSONArray jsonArray = new JSONArray();
            Map.Entry<String, Integer> next = iterator.next();
            jsonArray.add(next.getKey());
            jsonArray.add(next.getValue());
            list.add(jsonArray);
        }
        return new Result(Response.SC_OK,"OK",list);
    }

    @GetMapping(value = "getStationTop")
    public Result getStationTop(){
        List<StationTop> stationTops = new ArrayList<>();
        List<Station> stations = stationService.dirStationTop10(null);
        Iterator<Station> iterator = stations.iterator();
        while(iterator.hasNext()){
            StationTop stationTop = new StationTop();
            double recycleMoney = 0.0;
            double saleMoney = 0.0;
            Station next = iterator.next();
            List<Transaction> transaction = transactionService.getTransactionData(next.getUuid());
            for(int i=0;i<transaction.size();i++){
                recycleMoney += transaction.get(i).getAllMoney();
            }
            stationTop.setStationName(next.getStationName());
            stationTop.setMonthRecycle(recycleMoney);
            List<Trade> trades = tradeService.getTradeData(next.getUuid());
            for(int i=0;i<trades.size();i++){
                saleMoney += trades.get(i).getAllMoney();
            }
            stationTop.setMonthSale(saleMoney);
            char[] chars = next.getStationAddress().toCharArray();
            int left=0,right=0;
            for(int j=0;j<chars.length;j++){
                if(chars[j]=='市'){
                    left=j+1;
                }else if(chars[j]=='区'){
                    right=j;
                    break;
                }
            }
            String area = next.getStationAddress().substring(left,right+1);
            stationTop.setStationArea(area);
            stationTops.add(stationTop);
        }
        return new Result(Response.SC_OK,"OK",stationTops);
    }

    @GetMapping(value = "/getPieGoodsType")
    public Result getPieGoodsType(){
        List<TransactionGoods> transactionGoods = transactionGoodsService.dirTransactionGoods(null);
        int total = transactionGoods.size();
        Iterator<TransactionGoods> iterator = transactionGoods.iterator();
        Map<String,Integer> map = new HashMap<>();
        while (iterator.hasNext()){
            TransactionGoods tg = iterator.next();
            Goods goods = goodsService.getGoods(tg.getGoodsId());
            String goodsTypeName = goodsTypeService.getGoodsTypeName(goods.getGoodsType());
            if(!map.containsKey(goodsTypeName)){
                map.put(goodsTypeName,1);
            }else {
                map.put(goodsTypeName,map.get(goodsTypeName)+1);
            }
        }
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator1 = entries.iterator();
        JSONArray jsonArray = new JSONArray();
        while (iterator1.hasNext()){
            JSONObject jsonObject = new JSONObject();
            Map.Entry<String, Integer> next = iterator1.next();
            jsonObject.put("name",next.getKey());
            double pre = next.getValue()*1.0/total;
            jsonObject.put("value",pre);
            jsonArray.add(jsonObject);
        }

        return new Result(Response.SC_OK,"OK",jsonArray);
    }

    @GetMapping(value = "/getBarGoodsTop10")
    public Result getBarGoodsTop10(){
        List<TransactionGoods> transactionGoods = transactionGoodsService.dirTransactionGoods(null);
        int total = transactionGoods.size();
        Iterator<TransactionGoods> iterator = transactionGoods.iterator();
        Map<String,Integer> map = new HashMap<>();
        while (iterator.hasNext()){
            TransactionGoods tg = iterator.next();
            Goods goods = goodsService.getGoods(tg.getGoodsId());
            if(!map.containsKey(goods.getGoodsName())){
                map.put(goods.getGoodsName(),1);
            }else {
                map.put(goods.getGoodsName(),map.get(goods.getGoodsName())+1);
            }
        }
        Map<String,Integer> newMap = EchartsController.sortDescend(map);
        Set<Map.Entry<String, Integer>> entries = newMap.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator1 = entries.iterator();
        JSONArray jsonArray = new JSONArray();
        while (iterator1.hasNext()){
            JSONObject jsonObject = new JSONObject();
            Map.Entry<String, Integer> next = iterator1.next();
            jsonObject.put("name",next.getKey());
            jsonObject.put("value",next.getValue());
            jsonArray.add(jsonObject);
        }
        return new Result(Response.SC_OK,"OK",jsonArray);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortDescend(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> returnMap = new LinkedHashMap<K, V>();
        int count=0;
        for (Map.Entry<K, V> entry : list) {
            returnMap.put(entry.getKey(), entry.getValue());
            count++;
            if(count>=10){
                break;
            }
        }
        return returnMap;
    }
}
