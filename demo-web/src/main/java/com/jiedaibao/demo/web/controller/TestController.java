package com.jiedaibao.demo.web.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiedaibao.demo.biz.bean.Params;
import com.jiedaibao.demo.biz.bean.Response;

@RestController
@RequestMapping("/test")
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = "/t")
	public Response test() throws Exception {
		log.info("start:{}", System.nanoTime());
		Response response = new Response();
		Params params = new Params();
		Date now = new Date();
		params.setFormatTime(now);
		params.setOriginTime(now);
		response.setData(params);
		return response;
	}
}
