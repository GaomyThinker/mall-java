package com.mooc.mall.enums;


import lombok.Getter;

/**
 * @Author gaomy
 * @Date 2022/2/23 16:06
 * @Description
 * @Version 1.0
 */

@Getter
public enum PaymentTypeEnum {
    PAY_ONLINE(1),
    ;
    Integer code;

    PaymentTypeEnum(Integer code) {
        this.code = code;
    }
}
