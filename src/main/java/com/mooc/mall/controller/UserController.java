package com.mooc.mall.controller;


import com.mooc.mall.pojo.User;
import com.mooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mooc.mall.enums.ResponseEnum.NEED_LOGIN;

/**
 * @Author gaomy
 * @Date 2021/12/31 16:30
 * @Description
 * @Version 1.0
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @PostMapping("/register")
    public ResponseVo register(@RequestBody User user){
        log.info(user.toString());
//        log.info(ResponseEnum.SUCCESS.getDesc()+"------"+ResponseEnum.SUCCESS.name());
//        return ResponseVo.success();
//        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        return ResponseVo.error(NEED_LOGIN);
    }

//    @PostMapping("/register")
//    public ResponseVo register(@RequestParam String  username){
//        log.info(username);
//        return ResponseVo.success("注册成功");
//    }
}
