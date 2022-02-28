package com.mooc.mall.form;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author gaomy
 * @Date 2022/2/18 14:40
 * @Description  购物车添加商品
 * @Version 1.0
 */

@Data
public class CartAddForm {

    @NotNull
    private Integer productId;


    private Boolean selected=true;
}
