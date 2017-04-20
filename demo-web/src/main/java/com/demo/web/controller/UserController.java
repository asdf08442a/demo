package com.demo.web.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.demo.api.dto.response.Response;
import com.demo.common.enums.DateStyle;
import com.demo.common.util.DateUtils;
import com.demo.dao.entity.User;
import com.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@Valid @RequestBody User user, BindingResult bindingResult) {
        user.setBizDate(DateUtils.dateToString(new Date(), DateStyle.YYYYMMDD));
        if (userService.insert(user))
            return new Response().success();
        else
            return new Response().failure();
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Response get(@NotNull @RequestBody String id, BindingResult bindingResult) {
        User user = userService.selectOne(new EntityWrapper<User>().eq("id","1"));
        return new Response().success(user);
    }

}
