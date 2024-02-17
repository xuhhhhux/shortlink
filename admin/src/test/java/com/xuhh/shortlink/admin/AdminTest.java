package com.xuhh.shortlink.admin;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminTest {
    @Autowired
    private RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    @Test
    public void test() {
        userRegisterCachePenetrationBloomFilter.delete();
    }
}
