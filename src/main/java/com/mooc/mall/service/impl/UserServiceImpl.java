package com.mooc.mall.service.impl;


import com.mooc.mall.dao.CategoryMapper;
import com.mooc.mall.dao.UserMapper;
import com.mooc.mall.enums.RoleEnum;
import com.mooc.mall.pojo.User;
import com.mooc.mall.service.IUserService;
import com.mooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.mooc.mall.enums.ResponseEnum.*;

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

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 注册
     * @param user
     */
    @Override
    public ResponseVo<User> register(User user) {
//        error();
        //username重复
        Integer countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername>0){
            return ResponseVo.error(USERNAME_EXIST);
        }

        //email不能重复
        Integer countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail>0){
            return ResponseVo.error(EMAIL_EXIST);
        }

        //写入数据库之前，密码要进行加密 MD5加密
        user.setPassword
                (DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));

        //写入数据库
        user.setRole(RoleEnum.CUSTOMER.getCode());
        int resultCount = userMapper.insertSelective(user);
        if (resultCount==0){
            return ResponseVo.error(ERROR);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (null == user){
            // 用户不存在(返回：用户名或密码错误)
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        if(!user.getPassword().equalsIgnoreCase
                (DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
            // 密码错误(返回：用户名或密码错误)
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        // 返回的用户信息不应该有密码
        user.setPassword(" ");
        return ResponseVo.success(user);
    }


//    public List<CategoryVo> getCategory(){
//        List<CategoryVo> all=new ArrayList<>();
    // 拿id去查谁的parent_id为这个id
//        List<CategoryVo> getAllRoot=categoryMapper.getAllChildren(0);
//        for (int i = 0; i < getAllRoot.size(); i++) {
//            List<CategoryVo> allChildren = getAllChildren(getAllRoot.get(i).getId());
//            getAllRoot.get(i).setSubCategories(allChildren);
//
//        }
//        all.addAll(getAllRoot);
//        return all;
//    }
//
//    public List<CategoryVo> getAllChildren(Integer id){
//        List<CategoryVo> allChildren = categoryMapper.getAllChildren(id);
//        for (int i = 0; i < allChildren.size(); i++) {
//            List<CategoryVo> allChildren1 = getAllChildren(allChildren.get(i).getId());
//            allChildren.get(i).setSubCategories(allChildren1);
//        }
//        return allChildren;
//    }

    // 模拟意外错误，对异常进行处理使返回的json字符串中的字段与我们自定义的保持一致
//    public void error(){
//        throw new RuntimeException("意外错误");
//    }


}
