package com.mooc.mall.pojo;


import lombok.Data;

/**
 * @Author gaomy
 * @Date 2022/2/18 15:33
 * @Description 购物车 添加到redis
 * @Version 1.0
 */

@Data
public class Cart {
    private Integer productId;

    private Integer quantity;

    private Boolean productSelected;

    public  Cart(){

    }

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }

}
