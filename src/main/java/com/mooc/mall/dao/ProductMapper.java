package com.mooc.mall.dao;

import com.mooc.mall.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;
@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectByCategoryIdSet(@Param(value = "categoryIdSet") Set<Integer> categoryIdSet);

    List<Product> selectByProductIdSet(@Param(value = "productIdSet") Set<Integer> categoryProductIdSet);
}