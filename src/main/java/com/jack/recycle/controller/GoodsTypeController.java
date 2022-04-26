package com.jack.recycle.controller;

import com.jack.recycle.model.GoodsType;
import com.jack.recycle.service.GoodsTypeService;
import com.jack.recycle.utils.DateUtils;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.StatusCode;
import com.jack.recycle.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/goodstype")
public class GoodsTypeController {
    @Autowired
    private GoodsTypeService goodsTypeService;

    @RequestMapping(value = "/dirGoodsType", method = RequestMethod.GET)
    public Result dirGoodsType() {
        List<GoodsType> goodsTypes = goodsTypeService.dirGoodsType();
        return new Result(StatusCode.OK, "OK", goodsTypes);
    }

    @DeleteMapping("/delGoodsType/{uuid}")
    public Result delGoodsType(@PathVariable String uuid) {
        int i = goodsTypeService.delGoodsType(uuid);
        return new Result(StatusCode.OK, "OK", i);
    }

    @RequestMapping(value = "/addGoodsType",method = RequestMethod.POST)
    public Result addGoodsType(@RequestBody GoodsType goodsType) {
        if (StringUtils.isEmpty(goodsType.getGoodsType())){
            return new Result(StatusCode.INTERNAL_SERVER_ERROR,"参数错误");
        }
        List<GoodsType> goodsTypes = goodsTypeService.dirGoodsType();
//        for (GoodsType type:goodsTypes){
//            if (goodsType.getGoodsType().equals(type.getGoodsType())){
//                return new Result(StatusCode.INTERNAL_SERVER_ERROR,"物品类型名字不能重复");
//            }
//        }
        List<GoodsType> multi = goodsTypes.stream().filter(type -> goodsType.getGoodsType().equals(type.getGoodsType())).collect(Collectors.toList());
        if (!ListUtils.isEmpty(multi)){
            return new Result(StatusCode.INTERNAL_SERVER_ERROR,"物品类型名字不能重复");
        }
        goodsType.setUuid(UUID.randomUUID().toString());
//        goodsType.setCreateUser(UserUtils.getCurrUserInfo().getUuid());
//        goodsType.setCreateTime(DateUtils.getFormatDate("yyyy-MM-dd hh:mm:ss"));
        int i = goodsTypeService.addGoodsType(goodsType);
        return new Result(StatusCode.OK, "OK", i);
    }
}
