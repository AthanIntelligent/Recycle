package com.jack.recycle.controller;

import com.alibaba.excel.util.DateUtils;
import com.jack.recycle.excel.ApiExcelListener;
import com.jack.recycle.model.GoodsExcel;
import com.jack.recycle.service.GoodsExcelService;
import com.jack.recycle.service.GoodsService;
import com.jack.recycle.utils.EasyExcelUtils;
import com.jack.recycle.utils.MemcachedRunner;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.util.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/goods/excel")
public class GoodsExcelController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MemcachedRunner memcachedRunner;

    @GetMapping(value = "/getTemplate")
    public void getTemplate() {
        String fileName="导入物品模板";
        String sheetName = "物品详情数据模板";
        EasyExcelUtils.export(fileName,null,sheetName, GoodsExcel.class);
    }

    @PostMapping("/uuid")
    public Map<String,String> generatorUUid(@RequestBody List<String> ids) throws Exception {
        if (CollectionUtils.isEmpty(ids)){
            throw new Exception();
        }
        Map map = new HashMap();
        String uuid = UUID.randomUUID().toString();
        MemcachedClient memcachedClient = memcachedRunner.getClient();
        memcachedClient.set("uuids", 100000, ids);
        map.put("uuid", uuid);
        return map;
    }


    @GetMapping(value = "/export")
    public void exportGoods(String uuid) throws Exception {
        if (StringUtils.isEmpty(uuid))
            throw new Exception("参数为空");
        MemcachedClient memcachedClient = memcachedRunner.getClient();
        List<String> goodsIds = (List) memcachedClient.get("uuids");
        if (CollectionUtils.isNotEmpty(goodsIds)){
            String fileName = "导入物品数据-" + DateUtils.format(new Date(),"yyyy-MM-dd");
            String sheetName = "物品详情数据";
            EasyExcelUtils.export(fileName, new GoodsExcelService().getGoodsList(goodsIds), sheetName, GoodsExcel.class);
            memcachedClient.delete(uuid);
        }
    }

    @PostMapping(value = "/import")
    public void importGoods(@RequestPart("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("导入文件不能为空");
        }
        BufferedInputStream bufferedInputStream = null;
        bufferedInputStream = new BufferedInputStream(file.getInputStream());
        ApiExcelListener importExcelListener = new ApiExcelListener(new GoodsExcelService());
        EasyExcelUtils.Import(bufferedInputStream, importExcelListener, GoodsExcel.class);
    }
}
