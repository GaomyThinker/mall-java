package com.mooc.mall.enums;


import lombok.Getter;

/**
 * @Author gaomy
 * @Date 2022/1/5 9:42
 * @Description
 * @Version 1.0
 */

@Getter
public enum ResponseEnum {
    ERROR(-1,"服务器错误"),

    SUCCESS(0,"成功"),

    PASSWORD_ERROR(1,"密码错误"),

    USER_EXIST(2,"用户已存在"),

    NEED_LOGIN(10,"用户未登录，请先登录"),
    ;

    Integer code;

    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
