package com.jiedaibao.demo.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiedaibao.demo.dao.bean.JyxdRepay;
import com.jiedaibao.demo.dao.mappers.JyxdRepayMapper;

public class TestService {

    private static final Logger log = LoggerFactory.getLogger(TestService.class);

    private JyxdRepayMapper     jyxdRepayMapper;

    public void get(String id) {
        log.info("start:{}",System.nanoTime());
        JyxdRepay jyxdRepay = jyxdRepayMapper.getById(id);
        System.out.println(jyxdRepay);
    }
}
