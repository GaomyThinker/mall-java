package com.mooc.mall.enums;


import lombok.Getter;

/**
 * @Author gaomy
 * @Date 2021/12/31 15:28
 * @Description
 * @Version 1.0
 */

@Getter
public enum RoleEnum {
    //0-管理员 1-普通用户
    ADMIN(0),
    CUSTOMER(1),
    ;
    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
