package com.jiedaibao.demo.biz.service;

import com.jiedaibao.demo.common.cache.RedisCache;
import com.jiedaibao.demo.common.util.DateUtils;
import com.jiedaibao.demo.dao.entity.User;
import com.jiedaibao.demo.dao.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private static final Logger log = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCache redisCache;

    public void get(String id) {
        log.info("start:{}", System.nanoTime());
        User user = new User();
        user.setName("aa");
        user.setAddr("bbbb");
        user.setAge(12);
        user.setBizDate(DateUtils.getTimeStampYmd());
        int ret = userMapper.insert(user);
        log.debug("insert result:{}", ret);

        redisCache.putCache(user.getName(),user);

        user = userMapper.getById(user.getId());
        log.debug("getById result:{}", user);

        List<User> users = new ArrayList<User>();
        User user1 = new User();
        user1.setName("qq");
        user1.setAddr("ww");
        user1.setAge(13);
        user1.setBizDate(DateUtils.getTimeStampYmd());
        users.add(user1);
        User user2 = new User();
        user2.setName("ee");
        user2.setAddr("rr");
        user2.setAge(14);
        user2.setBizDate(DateUtils.getTimeStampYmd());
        users.add(user2);
        ret = userMapper.batchInsert(users);
        log.debug("batchInsert result:{}", ret);

        user.setAge(15);
        ret = userMapper.update(user);
        log.debug("update result:{}", ret);
    }
}
