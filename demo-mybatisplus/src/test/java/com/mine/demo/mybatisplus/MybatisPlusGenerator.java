package com.mine.demo.mybatisplus;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.ConfigGenerator;

public class MybatisPlusGenerator {

	public static void main(String[] args) {
		ConfigGenerator cg = new ConfigGenerator();

		// 配置 MySQL 连接
		cg.setDbDriverName("com.mysql.jdbc.Driver");
		cg.setDbUser("root");
		cg.setDbPassword("123456");
		cg.setDbUrl("jdbc:mysql://127.0.0.1:3306/db_uc?characterEncoding=utf8");
		cg.setTableNames(new String[] { "user" });

		// 配置包名
		cg.setEntityPackage("com.mine.demo.mybatisplus.entity");
		cg.setMapperPackage("com.mine.demo.mybatisplus.mapper");
		cg.setXmlPackage("com.mine.demo.mybatisplus.mapper.xml");
		cg.setServicePackage("com.mine.demo.mybatisplus.service");
		cg.setServiceImplPackage("com.mine.demo.mybatisplus.service.impl");
		cg.setDbPrefix(true);
		cg.setIdType(IdType.ID_WORKER);

		// 配置保存路径
//		cg.setSaveDir("D://");

		// 其他参数请根据上面的参数说明自行配置，当所有配置完善后，运行AutoGenerator.run()方法生成Code
		// 生成代码
		AutoGenerator.run(cg);
	}

}
