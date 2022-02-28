package com.mooc.mall.vo;


import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author gaomy
 * @Date 2022/2/18 14:28
 * @Description 查询购物车列表时的cartProductList部分，是CartVo的一部分
 * @Version 1.0
 */

@Data
public class CartProductVo {
    private Integer productId;
    // 购买的数量
    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private  Integer productStatus;

    // 商品总价=数量*单价
    private BigDecimal productTotalPrice;

    private Integer productStock;

    // 商品是否选中
    private Boolean productSelected;

    public CartProductVo() {

    }

    public CartProductVo(Integer productId, Integer quantity, String productName, String productSubtitle, String productMainImage, BigDecimal productPrice, Integer productStatus, BigDecimal productTotalPrice, Integer productStock, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.productSubtitle = productSubtitle;
        this.productMainImage = productMainImage;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productTotalPrice = productTotalPrice;
        this.productStock = productStock;
        this.productSelected = productSelected;
    }
}

