package com.jack.recycle.controller;

import com.jack.recycle.model.Goods;
import com.jack.recycle.model.GoodsType;
import com.jack.recycle.model.VO.GoodsTypeAndGoods;
import com.jack.recycle.service.GoodsService;
import com.jack.recycle.service.GoodsTypeService;
import com.jack.recycle.utils.PicUtil;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.StatusCode;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @PostMapping(value = "/addGoods")
    public Result addGoods(@RequestBody Goods goods){
        goods.setUuid(UUID.randomUUID().toString());
        String goodsTypeUuid = goodsTypeService.getGoodsTypeUuid(goods.getGoodsType());
        goods.setGoodsType(goodsTypeUuid);
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
    public Result dirGoods(@RequestBody Goods goods) {
        if (!StringHelper.isNullOrEmptyString(goods.getGoodsType())){
            String goodsTypeUuid = goodsTypeService.getGoodsTypeUuid(goods.getGoodsType());
            goods.setGoodsType(goodsTypeUuid);
        }
        List<Goods> goodsList = goodsService.dirGoods(goods);
        goodsList.stream().forEach(good -> good.setGoodsType(goodsTypeService.getGoodsTypeName(good.getGoodsType())));
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
    public Result pic(MultipartFile file) throws IOException {
        String path = PicUtil.dealPic(file);
        if (!StringUtils.isEmpty(path)){
            return new Result(StatusCode.OK, "OK", path);
        }
        return new Result(StatusCode.BAD_REQUEST, "error", null);
    }
}
