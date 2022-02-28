package com.mooc.mall.service.impl;


import com.mooc.mall.consts.MallConst;
import com.mooc.mall.dao.CategoryMapper;
import com.mooc.mall.pojo.Category;
import com.mooc.mall.service.CategoryService;
import com.mooc.mall.vo.CategoryVo;
import com.mooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author gaomy
 * @Date 2022/1/28 14:53
 * @Description
 * @Version 1.0
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    // 得到所有商品类目
    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<Category> categories = categoryMapper.selectAll();

        // 查出parent_id为0的
//        List<CategoryVo> categoryVoList=new ArrayList<>();
//        for (Category category:categories) {
//            if (category.getParentId().equals(MallConst.ROOT_PARENT_ID)){
//                CategoryVo categoryVo=new CategoryVo();
//                BeanUtils.copyProperties(category,categoryVo);
//                categoryVoList.add(categoryVo);
//            }
//        }

        // 先查出全部的类目，方便下面的分级都从这些数据中查，因为一直查数据库会影响效率
        // lambda + stream
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(MallConst.ROOT_PARENT_ID))
                .map(this::categoryToCategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());

        // 查询子目录
        findSubCategory(categoryVoList,categories);

        return ResponseVo.success(categoryVoList);
    }


    /**
     * 把父级目录和总的数据当做参数，方便递归查询
     * @param categoryVoList
     * @param categories
     */
    private void findSubCategory(List<CategoryVo> categoryVoList,List<Category> categories){
        for (CategoryVo categoryVo:categoryVoList) {
            List<CategoryVo> subCategoryVoList =new ArrayList<>();

            for (Category category:categories) {
                // 如果查到内容，设置subCategory,继续往下查
                if (categoryVo.getId().equals(category.getParentId())){
                    CategoryVo subCategoryVo = categoryToCategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }
            }
            subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            categoryVo.setSubCategories(subCategoryVoList);
            findSubCategory(subCategoryVoList,categories);
        }
    }

    /**
     * 类的转化，从category转化为CategoryVo
     * @param category
     * @return
     */
    private CategoryVo categoryToCategoryVo(Category category){
        CategoryVo categoryVo=new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }






    // 得到所有的categoryId
    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();
        findSubCategoryId(id,resultSet,categories);

    }

    public void findSubCategoryId(Integer id, Set<Integer> resultSet,List<Category> categories) {
        for (Category category:categories){
            if (category.getParentId().equals(id)){
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(),resultSet,categories);
            }
        }

    }
}
