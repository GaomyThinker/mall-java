package com.mooc.mall.dao;

import com.mooc.mall.MallApplicationTests;
import com.mooc.mall.pojo.Category;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Author gaomy
 * @Date 2021/12/17 10:24
 * @Description
 * @Version 1.0
 */


public class CategoryMapperTest extends MallApplicationTests {
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void findById() {
        Category category = categoryMapper.findById(100001);
        System.out.println(category.toString());
    }

    @Test
    public void queryById() {
        Category category=categoryMapper.queryById(100001);
        System.out.println(category.toString());
    }
}