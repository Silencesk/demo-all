package com.mine.demo.mybatisplus.mapper;

import com.mine.demo.mybatisplus.BaseTest;
import com.mine.demo.mybatisplus.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户test
 *
 * @author liutao
 * @create 2017-03-22 上午8:10
 */

public class UserMapperTest extends BaseTest {
    @Autowired
    UserMapper userMapper;
    static final int INIT_SIZE = 20;

    @Test
    public void initMany(){
        for (int i=0; i<INIT_SIZE; i++){
            User u = new User();
            u.setAge(i);
            u.setDeptNo("D01");
            u.setName("test" + i);
            userMapper.insert(u);
        }
    }

}
