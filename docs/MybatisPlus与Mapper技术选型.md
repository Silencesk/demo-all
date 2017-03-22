# MybatisPlus与Mapper技术选型
## MybatisPlus特性
[参考这里](http://mp.baomidou.com/#/)
> * 无侵入：Mybatis-Plus 在 Mybatis 的基础上进行扩展，只做增强不做改变，引入 Mybatis-Plus 不会对您现有的 Mybatis 构架产生任何影响，而且 MP 支持所有 Mybatis 原生的特性
> * 依赖少：仅仅依赖 Mybatis 以及 Mybatis-Spring
> * 损耗小：启动即会自动注入基本CURD，性能基本无损耗，直接面向对象操作
> * 预防Sql注入：内置Sql注入剥离器，有效预防Sql注入攻击
> * 通用CRUD操作：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
> * 多种主键策略：支持多达4种主键策略（内含分布式唯一ID生成器），可自由配置，完美解决主键问题
> * 支持热加载：Mapper 对应的 XML 支持热加载，对于简单的 CRUD 操作，甚至可以无 XML 启动
> * 支持ActiveRecord：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可实现基本 CRUD 操作
> * 支持代码生成：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用（P.S. 比 Mybatis 官方的 Generator 更加强大！）
> * 支持自定义全局通用操作：支持全局通用方法注入（ Write once, use anywhere ）
> * 支持关键词自动转义：支持数据库关键词（order、key......）自动转义，还可自定义关键词
> * 内置分页插件：基于Mybatis物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于写基本List查询
> * 内置性能分析插件：可输出Sql语句以及其执行时间，建议开发测试时启用该功能，能有效解决慢查询
> * 内置全局拦截插件：提供全表 delete 、 update 操作智能分析阻断，预防误操作

## Mapper特性
[参考这里](http://git.oschina.net/free/Mapper)
* 通用CRUD操作
* 支持多数据库
* **spring-boot整合教程**
* 分页插件
* 代码生成
* **订制公共mapper接口**
* 通用的查询封装
* **对动态表的支持（即表的垂直切分，如user被划分为user_1,user_2,user_3这三张表）**
* 默认的下划线转驼峰配置

## MybatisPlus 与 Mapper 比较
* MybatisPlus的功能是由开源组织实现，代码的贡献人员较多，协同完成，更体现了开源的精神，目前的活跃度也较高，功能还在不断完善当中；而Mapper的功能主要由abel533个人实现，功能已经比较成熟，当前活跃度并不高。
* 两者都有着不错的文档（包括使用文档，版本日志。。。），其中MybatisPlus相关内容介绍都是基于spring xml形式配置的；而Mapper则有spring-boot集成的详细介绍；如果选用前者，则在spring-boot中的配置集成中需要花一些时间。
* MybatisPlus的创造理念：在 Mybatis 的基础上只做增强不做改变，这样同样保持了MybatisPlus的轻量简洁（无第三方依赖引入包）；而Mapper的创造理念是借鉴了的hibernate的思想，有些臃肿。
* 在原生mybatis的基础上，Mapper封装更多，程序逻辑也会更为复杂；而当前MybatisPlus的代码量不大，代码结构也更为清晰；如果我们需要实现一些自身特定需求，改造起来也会更为方便简单。此外，随着MybatisPlus不断完善，我们对改组件的掌控度也会更高。
* MybatisPlus只抽象出了一个公共maper接口，该接口包含了所有的公共方法；而Mapper对于某个方法都抽象出了一个mapper接口，这种方式可以根据自己的需求去继承自己所需的公共接口，可减少mapper层的复杂度
* 代码生成，MybatisPlus提供了两种方式main方式和maven插件，其中maven插件生成方式不可用，后续会完善；而Mapper使用maven插件生成，以覆盖和全量方式生成，只生成实体与mapper；代码生成工具两者都不可取，需要自行编写。

## 总结
综上，我觉得**MybatisPlus**更符合我们现在的需求，轻量，优雅，高效。
