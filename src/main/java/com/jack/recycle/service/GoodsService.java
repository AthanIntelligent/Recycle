package com.jack.recycle.service;

import com.jack.recycle.model.Goods;

import java.util.List;

public interface GoodsService {
    int addGoods(Goods goods);

    int delGoods(String uuid);

    int updGoods(Goods goods);

    List<Goods> dirGoods();

    Goods getGoods(String uuid);
}
