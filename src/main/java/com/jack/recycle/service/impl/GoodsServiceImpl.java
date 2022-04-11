package com.jack.recycle.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.jack.recycle.mapper.GoodsDao;
import com.jack.recycle.model.Goods;
import com.jack.recycle.model.GoodsExcel;
import com.jack.recycle.service.GoodsService;
import com.jack.recycle.utils.ExcelUtil;
import com.jack.recycle.utils.MemcachedRunner;
import com.jack.recycle.utils.StreamUtil;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private MemcachedRunner memcachedRunner;

    @Override
    public int addGoods(Goods goods) {
        int insert = goodsDao.insert(goods);
        return insert;
    }

    @Override
    public int delGoods(String uuid) {
        int i = goodsDao.deleteByPrimaryKey(uuid);
        return i;
    }

    @Override
    public int updGoods(Goods goods) {
        int i = goodsDao.updateByPrimaryKeySelective(goods);
        return i;
    }

    @Override
    public List<Goods> dirGoods(Goods goods) {
        List<Goods> goodsList = goodsDao.selectAll(goods);
        return goodsList;
    }

    @Override
    public Goods getGoods(String uuid) {
        Goods goods = goodsDao.selectByPrimaryKey(uuid);
        return goods;
    }

    @Override
    public void getTemplate(HttpServletResponse response) {
        String fileName = "模板下载.xlsx";
        File file = new File(fileName);
        if (!file.exists()) {
            //生成与类的属性相对应的Excel表格头（列名）
            ExcelWriter excelWriter = EasyExcel.write(file, GoodsExcel.class).build();
            try {
                //设置Excel的sheet页的名字
                WriteSheet writeSheet = EasyExcel.writerSheet().build();
                //填充数据，将集合中的数据填充到定义好的sheet页中
                excelWriter.write(null, writeSheet);
            } finally {
                if (excelWriter != null) {
                    excelWriter.finish();
                }
            }
        }
        try {
            StreamUtil.downloadAttach(file, fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Map<String, String> uuids(List<String> ids) {
        Map map = new HashMap();
        String uuid = UUID.randomUUID().toString();
        MemcachedClient memcachedClient = memcachedRunner.getClient();
        memcachedClient.set("uuids", 100000, ids);
        map.put("uuid", uuid);
        return map;
    }

    @Override
    public void exportGoods(String uuid) {
        List<String> ids = (List) memcachedRunner.getClient().get("uuids");
        if (CollectionUtils.isNotEmpty(ids)) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(date);
            memcachedRunner.getClient().delete("uuids");
            String fileName = "物品详情数据-" + dateStr;
            String sheetName = "物品详情信息";
            ExcelUtil.export(fileName,goodsDao.selectByIds(ids),sheetName,GoodsExcel.class);
        }
    }

    @Override
    public List<Goods> importGoods(MultipartFile file) {

//        ExcelUtil.import(file,Goods.class)

        return null;
    }

}


