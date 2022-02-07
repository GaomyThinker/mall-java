package com.mooc.mall.controller;


import com.mooc.mall.service.CategoryService;
import com.mooc.mall.vo.CategoryVo;
import com.mooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author gaomy
 * @Date 2022/1/28 15:07
 * @Description
 * @Version 1.0
 */

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAll(){
        ResponseVo<List<CategoryVo>> listResponseVo = categoryService.selectAll();
        return listResponseVo;
    }
}
