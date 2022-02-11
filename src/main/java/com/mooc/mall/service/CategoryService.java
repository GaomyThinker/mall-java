package com.mooc.mall.service;


import com.mooc.mall.vo.CategoryVo;
import com.mooc.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 * @Author gaomy
 * @Date 2022/1/28 14:53
 * @Description
 * @Version 1.0
 */


public interface CategoryService {

    ResponseVo<List<CategoryVo>> selectAll();

    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
