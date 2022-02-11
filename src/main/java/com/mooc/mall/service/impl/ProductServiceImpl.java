package com.mooc.mall.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mooc.mall.dao.ProductMapper;
import com.mooc.mall.enums.ResponseEnum;
import com.mooc.mall.pojo.Product;
import com.mooc.mall.service.CategoryService;
import com.mooc.mall.service.ProductService;
import com.mooc.mall.vo.ProductVo;
import com.mooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mooc.mall.enums.ProductStatusEnum.DELETE;
import static com.mooc.mall.enums.ProductStatusEnum.OFF_SALE;

/**
 * @Author gaomy
 * @Date 2022/2/7 11:17
 * @Description
 * @Version 1.0
 */

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
   private RedisTemplate redisTemplate;

    /**
     * 传入categoryId，分页查询所有categoryId类别的物品及其子类和子子类等所有的物品
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet=new HashSet<>();
        // 要先判断category是否为null，否则会set一个null值，使set的size为1，导致sql语句还会执行in（null)
        if (null!=categoryId){
            categoryService.findSubCategoryId(categoryId,categoryIdSet);
            categoryIdSet.add(categoryId);
        }

        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        // 对查出的productList转换为productVoList
        List<ProductVo> productVoList = products.stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());

        PageInfo pageInfo=new PageInfo<>(products);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }

    // @TODO 写此接口的controller
    @Override
    public ResponseVo<Product> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        // 只对确定的条件进行判断
        if (product.getStatus().equals(OFF_SALE) || product.getStatus().equals(DELETE)){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        return ResponseVo.success(product);
    }

    public void test(){
        System.out.println(redisTemplate);
    }
}
