package com.baomidou.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springboot.entity.User;
import com.baomidou.springboot.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping("/test1")
	public User test1() {
		return userService.selectById(1L);
	}

	/**
	 * 分页
	 */
	@RequestMapping("/test2")
	public Page<User> selectPageUser() {
		return userService.selectPage(new Page<User>(), null);
	}
}
