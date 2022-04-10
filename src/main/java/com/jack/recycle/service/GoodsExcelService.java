package com.jack.recycle.service;

import com.jack.recycle.excel.ApiExcelService;
import com.jack.recycle.model.Goods;
import com.jack.recycle.model.GoodsExcel;
import com.jack.recycle.model.GoodsType;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsExcelService implements ApiExcelService {
    @Autowired
    private static GoodsService goodsService;

    @Autowired
    private static GoodsTypeService goodsTypeService;

    @SneakyThrows
    @Override
    public void saveData(Object o) {
        GoodsExcel goodsExcel = (GoodsExcel) o;
        Goods goods = new Goods();
//        //检查物品类型 物品名称是否重复 重复不可以导入
//        GoodsType goodsTypeByName = goodsTypeService.getGoodsTypeByName(goodsExcel.getGoodsType());
//        if (goodsTypeByName!=null){
//            goods.setGoodsType(showGoodsType(goodsExcel.getGoodsType()));
//        }
//        Goods goodsByName = goodsService.getGoodsByName(goodsExcel.getGoodsName());
//        if (goodsByName!=null){
//            goods.setGoodsName(goodsExcel.getGoodsName());
//        }
        goods.setRecycleDetail(goodsExcel.getRecycleDetail());
        goods.setPerMoney(Double.parseDouble(goodsExcel.getPerMoney()));
        goods.setRemark(goodsExcel.getRemark());
        goodsService.addGoods(goods);
    }

    @Override
    public void saveBathData(List list) {
        List<GoodsExcel> goodsExcelList = list;
        for (GoodsExcel goodsExcel : goodsExcelList) {
            saveData(goodsExcel);
        }
    }

    @SneakyThrows
    public List getGoodsList(List<String> goodsIds){
        List<GoodsExcel> goodsExcelList = new ArrayList<>();
//        List<Goods> list = goodsService.getGoodsList(goodsIds);
//        for (Goods goods:list){
//            GoodsExcel goodsExcel = new GoodsExcel();
//            goodsExcel.setGoodsType(showGoodsTypeName(goods.getGoodsType()));
//            goodsExcel.setGoodsName(goods.getGoodsName());
//            goodsExcel.setRecycleDetail(goods.getRecycleDetail());
//            goodsExcel.setPerMoney(goods.getPerMoney().toString());
//            goodsExcel.setRemark(goods.getRemark());
//            goodsExcelList.add(goodsExcel);
//        }
        return goodsExcelList;
    }

    public static String showGoodsTypeName(String uuid){
//        GoodsType goodsType = goodsTypeService.getGoodsType(uuid);
//        if (goodsType!=null){
//            return goodsType.getGoodsType();
//        }
        return null;
    }

    public static String showGoodsType(String goodsTypeName){
//        GoodsType goodsType = goodsTypeService.getGoodsTypeByName(goodsTypeName);
//        if (goodsType!=null){
//            return goodsType.getGoodsType();
//        }
        return null;
    }
}
