package com.mooc.mall.controller;


import com.mooc.mall.consts.MallConst;
import com.mooc.mall.form.UserLoginForm;
import com.mooc.mall.form.UserRegisterForm;
import com.mooc.mall.pojo.User;
import com.mooc.mall.service.IUserService;
import com.mooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

import static com.mooc.mall.enums.ResponseEnum.PARAM_ERROR;


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

    @Autowired
    private IUserService iUserService;

    @PostMapping("/register")
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userForm,
                               BindingResult bindingResult){
       if (bindingResult.hasErrors()){
           log.info("注册提交的参数有误,{} {}",
                   Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                   bindingResult.getFieldError().getDefaultMessage());
           return ResponseVo.error(PARAM_ERROR,bindingResult);
       }

        User user=new User();
        BeanUtils.copyProperties(userForm,user);
        return  iUserService.register(user);


    }

    @PostMapping("/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  BindingResult bindingResult,
                                  HttpSession httpSession){
        if (bindingResult.hasErrors()){
            return ResponseVo.error(PARAM_ERROR,bindingResult);
        }

        ResponseVo<User> responseVo=iUserService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        //设置session
        // session存在内存里 改进版：token+redis
        httpSession.setAttribute(MallConst.CURRENT_USER,responseVo.getData());
        return responseVo;


    }

    @GetMapping
    public ResponseVo<User> userInfo(HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        // 因为加了拦截器，能走到这个方法说明是登陆了的，所以不需要再判断
//        if (null==user){
//            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
//        }
        return ResponseVo.success(user);
    }

    //  判断登陆状态、拦截器
    @PostMapping("/logout")
    /**
     * {@link org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory} getSessionTimeoutInMinutes()
     */
    public ResponseVo logout(HttpSession session){
        // 去掉的原因同上
//        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
//        if (null==user){
//            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
//        }
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.successByMsg("退出成功");
    }


    @PostMapping("/test")
    public void test(User user, UserRegisterForm userForm){
        log.info("test,{}"+user.toString()+userForm.toString());
    }
//    @PostMapping("/register")
//    public ResponseVo register(@RequestParam String  username){
//        log.info(username);
//        return ResponseVo.success("注册成功");
//    }

    @PostMapping("/test1")
    public void test1(@RequestParam User user){
        log.info("test,{}"+user.toString());
    }
}
