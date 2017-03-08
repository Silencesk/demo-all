package com.mine.demo.mybatisplus.service;

import com.mine.demo.mybatisplus.entity.Role;
import com.mine.demo.mybatisplus.mapper.RoleMapper;
import com.mine.demo.mybatisplus.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper mapper;

    public Role selectByPrimaryKey(Integer id){
        return mapper.selectByPrimaryKey(id);
    }

    public RoleVO selectOne(Integer id){
        return mapper.selectOne(id);
    }
}