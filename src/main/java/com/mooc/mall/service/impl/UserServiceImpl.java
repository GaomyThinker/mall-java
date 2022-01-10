package com.mooc.mall.service.impl;


import com.mooc.mall.dao.UserMapper;
import com.mooc.mall.pojo.User;
import com.mooc.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @Author gaomy
 * @Date 2021/12/31 14:56
 * @Description
 * @Version 1.0
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * 注册
     * @param user
     */
    @Override
    public void register(User user) {
        //username重复
        Integer countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername>0){
            throw new RuntimeException("该username已注册");
        }

        //email不能重复
        Integer countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail>0){
            throw new RuntimeException("该email已注册");
        }

        //写入数据库之前，密码要进行加密 MD5加密
        user.setPassword
                (DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));

        //写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount==0){
            throw new RuntimeException("注册失败");
        }
    }
}
