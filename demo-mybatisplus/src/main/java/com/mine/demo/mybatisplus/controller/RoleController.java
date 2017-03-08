package com.mine.demo.mybatisplus.controller;

import com.google.common.collect.Maps;
import com.mine.demo.mybatisplus.entity.Role;
import com.mine.demo.mybatisplus.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService service;

	/**
	 * test vo
	 */
	@RequestMapping("/test1")
	public Role test1() {
		Role m = service.selectByPrimaryKey(1);
		System.err.println("查询实体：" + m);
		System.err.println("查询VO：" + service.selectOne(1));
		return m;
	}

	/**
	 * 分页
	 */
	@RequestMapping("/test2")
	public Map<String, Object> test2() {
		Map<String, Object> resultMap = Maps.newHashMap();
		return resultMap;
	}

}
