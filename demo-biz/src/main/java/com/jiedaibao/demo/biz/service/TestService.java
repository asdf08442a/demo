package com.jiedaibao.demo.biz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiedaibao.demo.dao.bean.JyxdRepay;
import com.jiedaibao.demo.dao.mappers.JyxdRepayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private static final Logger log = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private JyxdRepayMapper jyxdRepayMapper;

    public void get(String id) {
        log.info("start:{}", System.nanoTime());
        JyxdRepay jyxdRepay = jyxdRepayMapper.getById(id);
        log.debug("result:{}", jyxdRepay);
    }
}
