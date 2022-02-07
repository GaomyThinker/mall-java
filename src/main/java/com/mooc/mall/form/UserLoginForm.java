package com.mooc.mall.form;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author gaomy
 * @Date 2022/1/18 15:09
 * @Description
 * @Version 1.0
 */

@Data
public class UserLoginForm {

    @NotBlank(message = "用户名不能为空")
    // 用于String 判断空格
//    @NotEmpty 用于集合，检查集合里面是不是空的
//    @NotNull
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
