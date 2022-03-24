package com.jack.recycle;

import com.jack.recycle.mapper.GoodsTypeDao;
import com.jack.recycle.mapper.UserDao;
import com.jack.recycle.model.GoodsType;
import com.jack.recycle.model.User;
import com.jack.recycle.utils.MemcachedRunner;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
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

    @Autowired
    private MemcachedRunner memcachedRunner;
    @Test
    public void testSetGet()  {
        MemcachedClient memcachedClient = memcachedRunner.getClient();
        //使用中先测试插入一个 key 为 key ，
        //1000 为过期时间，单位为秒，0为永不过期
        //最后的 “000” 为 key 对应的值
        memcachedClient.set("key", 5000, "000");
        System.out.println("***********  "+memcachedClient.get("key").toString());
    }
}
