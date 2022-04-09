package com.jack.recycle.controller;

import com.jack.recycle.model.Goods;
import com.jack.recycle.service.GoodsService;
import com.jack.recycle.utils.PicUtil;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.StatusCode;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping(value = "/addGoods")
    public Result addGoods(Goods goods, MultipartFile file) {
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
    public Result updGoods(Goods goods,@RequestPart("file") MultipartFile file) {
        String path = PicUtil.dealPic(file);
        if (!"0".equals(path)){
            goods.setPic(path);
        }
        int i = goodsService.updGoods(goods);
        return new Result(StatusCode.OK, "OK", i);
    }

    @PostMapping(value = "/dirGoods")
    public Result dirGoods(@RequestBody Goods goods) {
        List<Goods> goodsList = goodsService.dirGoods(goods);
        return new Result(StatusCode.OK, "OK", goodsList);
    }

    @GetMapping(value = "/getGoods")
    public Result getGoods(String uuid) {
        Goods goods = goodsService.getGoods(uuid);
        return new Result(200, "ok", goods);
    }

    @GetMapping(value = "/getTemplate")
    public Result getTemplate(HttpServletResponse response) {
        goodsService.getTemplate(response);
        return new Result(StatusCode.OK, "OK", null);
    }

    @PostMapping("/uuids")
    public Map<String,String> generatorUUid(@RequestBody List<String> ids) throws Exception {
        if (CollectionUtils.isEmpty(ids)){
            throw new Exception();
        }
        Map<String, String> map = goodsService.uuids(ids);
        return map;
    }

    @GetMapping(value = "/exportGoods")
    public Result exportGoods(String uuid) throws Exception {
        if (StringUtils.isEmpty(uuid)){
            throw new Exception();
        }
        goodsService.exportGoods(uuid);
        return new Result(StatusCode.OK, "OK", null);
    }

    @PostMapping(value = "/import")
    public Result importGoods(@RequestPart("file") MultipartFile file) {

        goodsService.importGoods(file);
        return new Result(StatusCode.OK, "OK", null);
    }
}
