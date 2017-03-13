package com.jiedaibao.demo.web.controller;

import java.sql.SQLException;
import java.util.Date;

import com.jiedaibao.demo.biz.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiedaibao.demo.biz.bean.Params;
import com.jiedaibao.demo.biz.bean.Response;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/t")
    public Response test() throws Exception {
        log.info("start:{}", System.nanoTime());
        testService.get("1");
        Response response = new Response();
        Params params = new Params();
        Date now = new Date();
        params.setFormatTime(now);
        params.setOriginTime(now);
        response.setData(params);
//        throw new SQLException("aaa");
        return response;
    }
}
