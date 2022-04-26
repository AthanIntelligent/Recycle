package com.jack.recycle.controller;

import com.jack.recycle.model.TypeOfStation;
import com.jack.recycle.service.TypeOfStationService;
import com.jack.recycle.utils.Result;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/typeofstation")
public class TypeOfStationController {
    @Autowired
    TypeOfStationService typeOfStationService;
    /**
     * 添加基站所属物品类型
     */
    @PostMapping(value = "/addTypeOfStation")
    public Result addTypeOfStation(@RequestBody TypeOfStation typeOfStation){
        return new Result(Response.SC_OK,"success",typeOfStationService.addTypeOfStation(typeOfStation));
    }
}
