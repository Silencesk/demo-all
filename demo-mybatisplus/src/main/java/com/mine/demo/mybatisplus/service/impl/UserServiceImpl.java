package com.mine.demo.mybatisplus.service.impl;

import com.mine.demo.mybatisplus.entity.User;
import com.mine.demo.mybatisplus.service.IUserService;
import org.springframework.stereotype.Service;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.mine.demo.mybatisplus.mapper.UserMapper;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements IUserService {

}