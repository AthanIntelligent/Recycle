package com.jack.recycle.controller;

import com.jack.recycle.model.Manufacturers;
import com.jack.recycle.service.ManufacturersService;
import com.jack.recycle.utils.Result;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/facture")
public class ManufacturersController {
    @Autowired
    ManufacturersService manufacturersService;

    @PostMapping("/addManufacturers")
    public Result addManufacturers(@RequestBody Manufacturers manufacturers){
        return new Result(Response.SC_OK,"OK",manufacturersService.addManufacturer(manufacturers));
    }

    @GetMapping("/delManufacturers")
    public Result delManufacturers(String uuid){
        return new Result(Response.SC_OK,"OK",manufacturersService.removeManufacturer(uuid));
    }

    @GetMapping("/getManufacturers")
    public Result getManufacturers(String uuid){
        return new Result(Response.SC_OK,"OK",manufacturersService.getManufacturer(uuid));
    }

    @PostMapping("/dirManufacturers")
    public Result dirManufacturers(@RequestBody Manufacturers manufacturers){
        return new Result(Response.SC_OK,"OK",manufacturersService.dirManufacturer(manufacturers));
    }


}
