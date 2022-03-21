package com.jack.recycle;

import com.jack.recycle.mapper.GoodsTypeDao;
import com.jack.recycle.mapper.UserDao;
import com.jack.recycle.model.GoodsType;
import com.jack.recycle.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RecycleApplicationTests {
    @Autowired
    UserDao userDao;
    @Test
    void contextLoads() {
        User user = userDao.selectByUserName("admin");
        System.out.println(user);
    }

    @Autowired
    private GoodsTypeDao goodsTypeDao;
    @Test
    void contextLoadsA() {
        List<GoodsType> goodsTypes = goodsTypeDao.selectAll();
    }

}
