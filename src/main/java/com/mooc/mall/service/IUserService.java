package com.mooc.mall.service;


import com.mooc.mall.pojo.User;
import com.mooc.mall.vo.ResponseVo;

/**
 * @Author gaomy
 * @Date 2021/12/31 14:53
 * @Description
 * @Version 1.0
 */


public interface IUserService {

    /**
     * 注册
     */
    ResponseVo<User> register(User user);


    /**
     * 登录
     */
    ResponseVo<User> login(String username,String password);


}
