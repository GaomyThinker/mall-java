package com.mooc.mall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mooc.mall.MallApplicationTests;
import com.mooc.mall.pojo.Product;
import com.mooc.mall.service.ProductService;
import com.mooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Author gaomy
 * @Date 2022/2/7 11:33
 * @Description
 * @Version 1.0
 */

@Slf4j
public class ProductServiceImplTest extends MallApplicationTests {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private CategoryServiceImpl categoryService;

    private Gson gson=new GsonBuilder().setPrettyPrinting().create();

    private Gson  g=new Gson();

    @Test
    public void list() throws JSONException {
        JSONObject idObj = new JSONObject();
        idObj.put("id", "111111");
        System.out.println(idObj);


        ResponseVo<Product> detail = productServiceImpl.detail(26);
        Product data = detail.getData();
        log.info(data+"{}");
        log.info(gson.toJson(detail));

//        ResponseVo<PageInfo> list = productService.list(null,0,0);
//        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),list.getStatus());
////        Set<Integer> set=new HashSet<>();
//        if (null==set){
//            System.out.println(true);
//        }else {
//            System.out.println(false);
//        }
//        System.out.println(set);
    }
    @Test
    public void test(){
        System.out.println("-----------------------------");
    }
}