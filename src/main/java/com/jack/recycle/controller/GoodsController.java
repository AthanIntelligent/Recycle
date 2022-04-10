package com.jack.recycle.controller;

import com.jack.recycle.model.Goods;
import com.jack.recycle.service.GoodsService;
import com.jack.recycle.service.GoodsTypeService;
import com.jack.recycle.utils.PicUtil;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.StatusCode;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @PostMapping(value = "/addGoods")
    public Result addGoods(Goods goods, MultipartFile file) throws IOException {
        String path = PicUtil.dealPic(file);
        if (!"0".equals(path)){
            goods.setPic(path);
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
    public Result updGoods(Goods goods,@RequestPart("file") MultipartFile file) throws IOException {
        String path = PicUtil.dealPic(file);
        if (!"0".equals(path)){
            goods.setPic(path);
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

    @GetMapping(value = "/getGoods")
    public Result getGoods(String uuid) {
        Goods goods = goodsService.getGoods(uuid);
        return new Result(200, "ok", goods);
    }


}
