package com.mooc.mall.form;


import lombok.Data;

/**
 * @Author gaomy
 * @Date 2022/2/21 16:44
 * @Description
 * @Version 1.0
 */

@Data
public class CartUpdateForm {

    private  Integer quantity;

    private Boolean selected;
}
