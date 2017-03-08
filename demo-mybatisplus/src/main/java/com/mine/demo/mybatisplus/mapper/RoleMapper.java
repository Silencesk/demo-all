package com.mine.demo.mybatisplus.mapper;

import com.mine.demo.mybatisplus.entity.Role;
import com.mine.demo.mybatisplus.vo.RoleVO;

/**
 *
 * User 表数据库控制层接口
 *
 */
public interface RoleMapper {

    Role selectByPrimaryKey(Integer id);

    /**
     * 无需指定RoleVO的映射关系，可直接进行转换
     * @param id
     * @return
     */
//    @Select("SELECT id,role_name,enable_flag FROM role WHERE id = #{id}")
    RoleVO selectOne(Integer id);

}