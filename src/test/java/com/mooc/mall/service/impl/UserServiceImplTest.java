package com.mooc.mall.service.impl;

import com.mooc.mall.MallApplicationTests;
import com.mooc.mall.enums.RoleEnum;
import com.mooc.mall.pojo.User;
import com.mooc.mall.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author gaomy
 * @Date 2021/12/31 15:23
 * @Description
 * @Version 1.0
 */

@Transactional
public class UserServiceImplTest extends MallApplicationTests {

    @Autowired
    private IUserService iUserService;

    @Test
    public void register() {
        User user=new User("java","123456","java@qq.com", RoleEnum.CUSTOMER.getCode());
        iUserService.register(user);
    }
}