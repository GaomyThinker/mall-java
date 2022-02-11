package com.mooc.mall.enums;


import lombok.Getter;

/**
 * @Author gaomy
 * @Date 2022/2/8 15:18
 * @Description
 * @Version 1.0
 */

@Getter
public enum ProductStatusEnum {

    ON_SALE(1),

    OFF_SALE(2),

    DELETE(3),

    ;
    Integer code;

    ProductStatusEnum(Integer code){
        this.code=code;
    }
}
