package com.demo.web.controller;

import com.demo.common.cache.RedisCache;
import com.demo.dao.entity.User;
import com.demo.web.dto.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * redis test
 *
 * @author jinzg
 * @create 2017-04-27 11:13
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisCache redisCache;

    @RequestMapping(value = "/put", method = RequestMethod.POST)
    public Response add(@Valid @RequestBody User user, BindingResult bindingResult) {
        redisCache.putCache("user", user);
        return new Response().success();
    }
}
