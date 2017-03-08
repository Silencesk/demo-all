package com.mine.demo.mybatisplus.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mine.demo.mybatisplus.entity.User;
import com.mine.demo.mybatisplus.mapper.UserMapper;
import com.mine.demo.mybatisplus.service.IUserService;
import org.springframework.stereotype.Service;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}