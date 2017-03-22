package com.jiedaibao.demo.web.controller;

import com.jiedaibao.demo.api.dto.request.Params;
import com.jiedaibao.demo.api.dto.request.Request;
import com.jiedaibao.demo.api.dto.response.Response;
import com.jiedaibao.demo.biz.service.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private ITestService testService;

    @RequestMapping(value = "/t")
    public Response test() {
        log.info("start:{}", System.nanoTime());
        testService.get("1");
        Params params = new Params();
        params.setFormatTime(new Date());
        params.setOriginTime(new Date());
//        throw new SQLException("aaa");
        return new Response().success(params);
    }

    @RequestMapping(value = "/t")
    public Response test1(@Valid @RequestBody Request request, BindingResult bindingResult) {
        log.info("start:{}", System.currentTimeMillis());
        return new Response().success();
    }
}
