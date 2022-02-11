package com.mooc.mall.controller;


import com.github.pagehelper.PageInfo;
import com.mooc.mall.service.ProductService;
import com.mooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author gaomy
 * @Date 2022/2/7 15:17
 * @Description
 * @Version 1.0
 */

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseVo<PageInfo> list(@RequestParam(required = false) Integer categoryId,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize){
        ResponseVo<PageInfo> list = productService.list(categoryId, pageNum, pageSize);
        return list;
    }
}
