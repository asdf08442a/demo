package com.demo.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.demo.dao.entity.User;
import com.demo.dao.mappers.UserMapper;
import com.demo.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


}
