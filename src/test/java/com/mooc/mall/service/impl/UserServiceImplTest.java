package com.mooc.mall.service.impl;

import com.mooc.mall.MallApplicationTests;
import com.mooc.mall.enums.ResponseEnum;
import com.mooc.mall.enums.RoleEnum;
import com.mooc.mall.pojo.User;
import com.mooc.mall.service.IUserService;
import com.mooc.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Before;
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

    @Autowired
    private UserServiceImpl userService;

    public static final String USERNAME="admin1";

    public static final String PASSWORD="123456";



    @Before
    public void register() {
        User user=new User(USERNAME,PASSWORD,"java@qq.com", RoleEnum.CUSTOMER.getCode());
        iUserService.register(user);
    }

    @Test
    public void login(){
        //因为注册方法加了@Before,所以不用再特意调用
//        register();
        ResponseVo<User> login = iUserService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),login.getStatus());
    }

//    @Test
//    public void getAllCategories(){
//        List<CategoryVo> category = userService.getCategory();
//        System.out.println(category);
//    }

}