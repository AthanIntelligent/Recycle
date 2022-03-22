package com.jack.recycle.controller;

import com.jack.recycle.model.Goods;
import com.jack.recycle.service.GoodsService;
import com.jack.recycle.utils.PicUtil;
import com.jack.recycle.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping(value = "/addGoods")
    public Result addGoods(Goods goods, MultipartFile file) {
        String path = PicUtil.dealPic(file);
        if (path!="0"){
            goods.setPic(path);
        }
        int i = goodsService.addGoods(goods);
        return new Result(200, "ok", i);
    }

    @DeleteMapping("/delGoods/{uuid}")
    public Result delGoods(@PathVariable String uuid) {
        int i = goodsService.delGoods(uuid);
        return new Result(200, "ok", i);
    }

    @PostMapping(value = "/updGoods")
    public Result updGoods(Goods goods, MultipartFile file) {
        String path = PicUtil.dealPic(file);
        goods.setPic(path);
        int i = goodsService.updGoods(goods);
        return new Result(200, "ok", i);
    }

    @GetMapping(value = "/dirGoods")
    public Result dirGoods() {
        List<Goods> goods = goodsService.dirGoods();
        return new Result(200, "ok", goods);
    }

    @GetMapping(value = "/getGoods")
    public Result getGoods(String uuid) {
        Goods goods = goodsService.getGoods(uuid);
        return new Result(200, "ok", goods);
    }




}
