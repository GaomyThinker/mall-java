package com.mooc.mall.dao;


import com.mooc.mall.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 开发生成器：连接数据库->获取表结构->生成文件
 * @Author gaomy
 * @Date 2021/12/16 16:55
 * @Description
 * @Version 1.0
 */

//@Mapper
public interface CategoryMapper {
    @Select("select * from mall_category where id=#{id}")
    Category findById(@Param("id") Integer id);

    Category queryById(Integer id);
}
