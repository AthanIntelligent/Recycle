package com.jack.recycle.controller;

import com.jack.recycle.model.Goods;
import com.jack.recycle.model.GoodsOfStation;
import com.jack.recycle.model.GoodsType;
import com.jack.recycle.model.VO.GoodsTypeAndGoods;
import com.jack.recycle.service.GoodsOfStationService;
import com.jack.recycle.service.GoodsService;
import com.jack.recycle.service.GoodsTypeService;
import com.jack.recycle.service.UserService;
import com.jack.recycle.utils.*;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.catalina.connector.Response;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsOfStationService goodsOfStationService;

    @PostMapping(value = "/addGoods")
    public Result addGoods(@RequestBody Goods goods){
        goods.setUuid(UUID.randomUUID().toString());
        String goodsTypeUuid = goodsTypeService.getGoodsTypeUuid(goods.getGoodsType());
        goods.setGoodsType(goodsTypeUuid);
        goods.setCreateUser(UserUtils.getCurrUserInfo().getUuid());
        goods.setCreateTime(DateUtils.getFormatDate("yyyy-MM-dd hh:mm"));
        //基站人员添加物品还需要添加到goodsOfStation表中
        String currentUserId = UserUtils.getCurrUserInfo().getUuid();
        if (!UserUtils.ADMIN.equals(currentUserId)){
            GoodsOfStation goodsOfStation = goodsOfStationService.getGoodsOfStationByStationLegal(currentUserId);
            String goodsIds = goodsOfStation.getGoodsIds();
            goodsIds += ","+goods.getUuid();
            goodsOfStation.setGoodsIds(goodsIds);
            int a = goodsOfStationService.updGoodsOfStation(goodsOfStation);
        }
        int i = goodsService.addGoods(goods);
        return new Result(StatusCode.OK, "OK", i);
    }

    @DeleteMapping("/delGoods/{uuid}")
    public Result delGoods(@PathVariable String uuid) {
        int i = goodsService.delGoods(uuid);
        return new Result(StatusCode.OK, "OK", i);
    }

    @PostMapping(value = "/updGoods")
    public Result updGoods(@RequestBody Goods goods){
        Goods goodsOld = goodsService.getGoods(goods.getUuid());
        goods.setGoodsType(goodsTypeService.getGoodsTypeUuid(goods.getGoodsType()));
        if (!StringUtils.isEmpty(goodsOld.getPic())){
            goods.setPic(goodsOld.getPic());
        }
        int i = goodsService.updGoods(goods);
        return new Result(StatusCode.OK, "OK", i);
    }

    @PostMapping(value = "/dirGoods")
    public Result dirGoods(@RequestBody Goods goods){
        if(UserUtils.isTokenExpect()){
            return new Result(Response.SC_BAD_REQUEST,"token过期");
        }
        if (!StringHelper.isNullOrEmptyString(goods.getGoodsType())){
            String goodsTypeUuid = goodsTypeService.getGoodsTypeUuid(goods.getGoodsType());
            goods.setGoodsType(goodsTypeUuid);
        }
        if(UserUtils.getCurrUserInfo().equals("") || UserUtils.getCurrUserInfo()==null){
            return new Result(Response.SC_BAD_REQUEST,"token过期");
        }
        String currUserId = UserUtils.getCurrUserInfo().getUuid();
        List<Goods> goodsList = goodsService.dirGoods(goods);
        if (UserUtils.ADMIN.equals(currUserId)) {
            goodsList.stream().forEach(good -> {
                good.setIsVisible(true);
            });
        }else {
            String goodsIds = goodsOfStationService.getGoodsOfStationByStationLegal(currUserId).getGoodsIds();
            List<String> goodsIdList = Arrays.asList(goodsIds.split(","));
            //废纸类有三个 1234 ，1 2是系统管理员添加 3是自己添加的 4是其他人添加的；那么goodsList就是1234 ；goodsIdList基站人员经营的所有物品的id集合，包括废纸类 其他类
            //做法：先循环那么goodsList就是1234，把不是自己添加不是管理员添加的排除掉
            Iterator<Goods> iterator = goodsList.iterator();
            while (iterator.hasNext()){
                Goods g = iterator.next();
                if(!goodsIdList.contains(g.getUuid())){
                    iterator.remove();
                }
            }

            for (Goods good:goodsList) {
                //判断哪些是可操作的 哪些是不可操作的
                if (UserUtils.ADMIN.equals(good.getCreateUser()))
                    good.setIsVisible(false);
                else
                    good.setIsVisible(true);
            }
        }
        goodsList.stream().forEach(good -> {
                good.setGoodsType(goodsTypeService.getGoodsTypeName(good.getGoodsType()));
                good.setCreateUser(userService.getRealNameByUuid(good.getCreateUser()));
        });
        return new Result(StatusCode.OK, "OK", goodsList);
    }

    @GetMapping(value = "/getGoodsTypeAndGoods")
    public Result getGoodsTypeAndGoods() {
        List<GoodsType> goodsTypesList = goodsTypeService.dirGoodsType();
        List<GoodsTypeAndGoods> result = new ArrayList<>();
        for (GoodsType goods:goodsTypesList){
            GoodsTypeAndGoods goodsTypeAndGoods = new GoodsTypeAndGoods();
            goodsTypeAndGoods.setGoodsType(goods.getGoodsType());
            Goods ggg = new Goods();
            ggg.setGoodsType(goods.getUuid());
            List<Goods> goodsByTypeId = goodsService.dirGoods(ggg);
            goodsTypeAndGoods.setGoodsList(goodsByTypeId);
            result.add(goodsTypeAndGoods);
        }
        return new Result(StatusCode.OK, "OK", result);
    }

    @GetMapping(value = "/getGoods")
    public Result getGoods(@RequestParam("uuid") String uuid) {
        Goods goods = goodsService.getGoods(uuid);
        String goodsTypeName = goodsTypeService.getGoodsTypeName(goods.getGoodsType());
        goods.setGoodsType(goodsTypeName);
        return new Result(200, "ok", goods);
    }


    @PostMapping(value = "/pic")
    public Result pic(MultipartFile file){
        String path = PicUtil.dealPic(file);
        if (!StringUtils.isEmpty(path)){
            return new Result(StatusCode.OK, "OK", path);
        }
        return new Result(StatusCode.BAD_REQUEST, "error", null);
    }
}
