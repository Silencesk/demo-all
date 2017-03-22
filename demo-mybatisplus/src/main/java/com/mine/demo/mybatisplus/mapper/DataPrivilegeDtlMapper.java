package com.mine.demo.mybatisplus.mapper;

import com.mine.demo.mybatisplus.privilege.DataPrivilegeDtlVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * 数据权限配置详情
 *
 */
public interface DataPrivilegeDtlMapper {

    public List<DataPrivilegeDtlVO> getDataPrivilegeDtl(@Param("privilegeId") String privilegeId, @Param("resCode") String resCode);

}