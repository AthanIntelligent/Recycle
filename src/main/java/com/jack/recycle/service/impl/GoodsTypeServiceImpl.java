package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.GoodsTypeDao;
import com.jack.recycle.model.GoodsType;
import com.jack.recycle.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    private GoodsTypeDao goodsTypeDao;

    @Override
    public List<GoodsType> dirGoodsType() {
        List<GoodsType> goodsTypes = goodsTypeDao.selectAll();
        return goodsTypes;
    }

    @Override
    public int delGoodsType(String uuid) {
        int i = goodsTypeDao.deleteByPrimaryKey(uuid);
        return i;
    }

    @Override
    public int addGoodsType(GoodsType goodsType) {
        goodsType.setUuid(UUID.randomUUID().toString());
        int insert = goodsTypeDao.insert(goodsType);
        return insert;
    }

    @Override
    public String getGoodsTypeUuid(String goodsType) {
        String goodsTypeUuid = goodsTypeDao.getGoodsTypeUuid(goodsType);
        return goodsTypeUuid;
    }

    @Override
    public String getGoodsTypeName(String goodsTypeUuid) {
        String goodsTypeName = goodsTypeDao.getGoodsTypeName(goodsTypeUuid);
        return goodsTypeName;
    }
}
