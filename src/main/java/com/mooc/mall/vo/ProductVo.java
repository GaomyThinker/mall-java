package com.mooc.mall.vo;


import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author gaomy
 * @Date 2022/2/7 11:12
 * @Description
 * @Version 1.0
 */

@Data
public class ProductVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private Integer status;

    private BigDecimal price;
}
