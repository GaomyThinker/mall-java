package com.mooc.mall.vo;


import lombok.Data;

import java.util.List;

/**
 * @Author gaomy
 * @Date 2022/1/27 16:40
 * @Description
 * @Version 1.0
 */

@Data
public class CategoryVo {
    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;

    private List<CategoryVo> subCategories;
}
