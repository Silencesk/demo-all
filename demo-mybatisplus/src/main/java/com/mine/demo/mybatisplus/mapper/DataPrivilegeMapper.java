package com.mine.demo.mybatisplus.mapper;

import com.mine.demo.mybatisplus.privilege.DataPrivilegeVO;

import java.util.List;

/**
 *
 * 数据权限列表
 *
 */
public interface DataPrivilegeMapper {
    public List<DataPrivilegeVO> getDataPrivileges();
}