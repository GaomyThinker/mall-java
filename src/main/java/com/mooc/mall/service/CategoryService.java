package com.mooc.mall.service;


import com.mooc.mall.vo.CategoryVo;
import com.mooc.mall.vo.ResponseVo;

import java.util.List;

/**
 * @Author gaomy
 * @Date 2022/1/28 14:53
 * @Description
 * @Version 1.0
 */


public interface CategoryService {

    ResponseVo<List<CategoryVo>> selectAll();
}
