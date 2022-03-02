package com.mooc.mall.enums;


import lombok.Getter;

/**
 * @Author gaomy
 * @Date 2022/2/23 16:09
 * @Description 0-已取消 10-未付款 20-已付款 40-已发货 50-交易成功 60-交易关闭
 * @Version 1.0
 */

@Getter
public enum OrderStatusEnum {
    CANCELED(0,"已取消"),

    NO_PAY(10,"未付款"),

    PAID(20,"已付款"),

    SHIPPED(40,"已发货"),

    TARED_SUCCESS(50,"交易成功"),

    TARED_CLOS(60,"交易关闭"),
    ;
    Integer code;

    String desc;

    OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
