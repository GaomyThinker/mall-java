package com.mooc.mall.pojo;


import lombok.Data;

import java.util.Date;

/**
 * @Author gaomy
 * @Date 2021/12/16 16:46
 * @Description
 * @Version 1.0
 */
@Data
public class Category {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

}
