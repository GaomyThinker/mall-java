package com.mooc.mall.form;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author gaomy
 * @Date 2022/3/1 15:23
 * @Description
 * @Version 1.0
 */

@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;
}
