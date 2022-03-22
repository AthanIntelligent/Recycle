package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.GoodsDao;
import com.jack.recycle.model.Goods;
import com.jack.recycle.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public int addGoods(Goods goods) {
        goods.setUuid(UUID.randomUUID().toString());
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
    public List<Goods> dirGoods() {
        List<Goods> goods = goodsDao.selectAll();
        return goods;
    }

    @Override
    public Goods getGoods(String uuid) {
        Goods goods = goodsDao.selectByPrimaryKey(uuid);
        return goods;
    }
}
