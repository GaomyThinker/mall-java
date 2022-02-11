package com.mooc.mall.service;


import com.github.pagehelper.PageInfo;
import com.mooc.mall.pojo.Product;
import com.mooc.mall.vo.ResponseVo;

/**
 * @Author gaomy
 * @Date 2022/2/7 11:17
 * @Description
 * @Version 1.0
 */


public interface ProductService {

    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<Product> detail(Integer productId);
}
