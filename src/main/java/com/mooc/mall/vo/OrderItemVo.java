package com.mooc.mall.vo;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author gaomy
 * @Date 2022/2/23 10:09
 * @Description
 * @Version 1.0
 */

@Data
public class OrderItemVo {

    private Long orderNo;

    private Integer productId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;
}
