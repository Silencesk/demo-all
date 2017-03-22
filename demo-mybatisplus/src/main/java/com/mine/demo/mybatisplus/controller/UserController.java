package com.mine.demo.mybatisplus.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mine.demo.mybatisplus.entity.User;
import com.mine.demo.mybatisplus.service.IUserService;
import com.mine.demo.mybatisplus.util.ThreadLocals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		System.err.println("查询：" + userService.selectById(1L));
		System.err.println("删除一条数据：" + userService.deleteById(1L));
		System.err.println("插入一条数据：" + userService.insertOrUpdate(new User(1L, "张三", 17, 1)));
		System.err.println("更新一条数据：" + userService.insertOrUpdate(new User(1L, "三毛", 18, 2)));

		User idUser = new User("张三", 17, 1);
		System.err.println("插入一条数据-数据库自增id：" + userService.insertOrUpdate(idUser));
		// ID_WORKER 18位
		System.err.println("插入一条数据-ID_WORKER：" + userService.insertOrUpdate(new User("张三", 17, 1)));

		return userService.selectById(1L);
	}

	/**
	 * 分页 PAGE
	 */
	@RequestMapping("/test2")
	public Page<User> test2() {
		// 设置模块
		ThreadLocals.setResCode("110110");
		EntityWrapper<User> ew = new EntityWrapper<User>();
//		ew.where("name='liutao'").where("test_id={0}","99");
		return userService.selectPage(new Page<User>(0, 12), ew);
	}

}
