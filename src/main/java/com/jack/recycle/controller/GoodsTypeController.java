package com.jack.recycle.controller;

import com.jack.recycle.model.GoodsType;
import com.jack.recycle.service.GoodsTypeService;
import com.jack.recycle.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goodstype")
public class GoodsTypeController {
    @Autowired
    private GoodsTypeService goodsTypeService;

    @RequestMapping("/dirGoodsType")
    public Result dirGoodsType(){
        List<GoodsType> goodsTypes = goodsTypeService.dirGoodsType();
        return new Result(200,"ok",goodsTypes);
    }
}
