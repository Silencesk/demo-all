package com.mine.demo.mybatisplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.mine.demo.mybatisplus.entity.User;
import com.mine.demo.mybatisplus.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	/**
	 * 增删改查 CRUD
	 */
	@RequestMapping("/test1")
	public User test1() {
		System.err.println("删除一条数据：" + userService.deleteById(1L));
		System.err.println("插入一条数据：" + userService.insertSelective(new User(1L, "张三", 17, 1)));
		System.err.println("插入一条数据：" + userService.insertSelective(new User(1L, "张三", 17, 1)));
		System.err.println("查询：" + userService.selectById(1L).toString());
		System.err.println("更新一条数据：" + userService.updateSelectiveById(new User(1L, "三毛", 18, 2)));
		return userService.selectById(1L);
	}

	/**
	 * 插入 OR 修改
	 */
	@RequestMapping("/test2")
	public User test2() {
//		userService.insertOrUpdateSelective(new User(1L, "王五", 19, 3));
		return userService.selectById(1L);
	}

	/**
	 * 分页 PAGE
	 */
	@RequestMapping("/test3")
	public Page<User> test3() {
		return userService.selectPage(new Page<User>(0, 12), null);
	}

}
