package com.jack.recycle.utils;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;

/**
 * Memcached缓存配置  MemcachedClient.getClient(),获取缓存
 */
@Component
public class MemcachedRunner implements CommandLineRunner {
    protected Logger logger =  LoggerFactory.getLogger(this.getClass());

    private MemcachedClient client = null;

    @Value("${memcache.ip}")
    private String ip;

    @Value("${memcache.port}")
    private int port;


    @Override
    public void run(String... args) throws Exception {
        try {
            client = new MemcachedClient(new InetSocketAddress(ip,port));
        } catch (IOException e) {
            logger.error("inint MemcachedClient failed ",e);
        }
    }
    public MemcachedClient getClient() {
        return client;
    }
}

