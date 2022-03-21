package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.GoodsTypeDao;
import com.jack.recycle.model.GoodsType;
import com.jack.recycle.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    private GoodsTypeDao goodsTypeDao;

    @Override
    public List<GoodsType> dirGoodsType() {
        List<GoodsType> goodsTypes = goodsTypeDao.selectAll();
        return goodsTypes;
    }
}
