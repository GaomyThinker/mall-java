package com.mooc.mall.vo;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author gaomy
 * @Date 2022/2/18 14:26
 * @Description
 * @Version 1.0
 */

@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private Boolean selectAll;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;
}
