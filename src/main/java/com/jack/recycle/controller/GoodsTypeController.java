package com.jack.recycle.controller;

import com.jack.recycle.model.GoodsType;
import com.jack.recycle.service.GoodsTypeService;
import com.jack.recycle.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goodstype")
public class GoodsTypeController {
    @Autowired
    private GoodsTypeService goodsTypeService;

    @RequestMapping(value = "/dirGoodsType", method = RequestMethod.GET)
    public Result dirGoodsType() {
        List<GoodsType> goodsTypes = goodsTypeService.dirGoodsType();
        return new Result(200, "ok", goodsTypes);
    }

    @DeleteMapping("/delGoodsType/{uuid}")
    public Result delGoodsType(@PathVariable String uuid) {
        int i = goodsTypeService.delGoodsType(uuid);
        return new Result(200, "ok", i);
    }

    @RequestMapping(value = "/addGoodsType",method = RequestMethod.POST)
    public Result addGoodsType(@RequestBody GoodsType goodsType) {
        int i = goodsTypeService.addGoodsType(goodsType);
        return new Result(200, "ok", i);
    }
}
