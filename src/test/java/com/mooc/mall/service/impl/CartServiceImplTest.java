package com.mooc.mall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mooc.mall.MallApplicationTests;
import com.mooc.mall.form.CartAddForm;
import com.mooc.mall.form.CartUpdateForm;
import com.mooc.mall.pojo.Cart;
import com.mooc.mall.service.CartService;
import com.mooc.mall.vo.CartVo;
import com.mooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;


/**
 * @Author gaomy
 * @Date 2022/2/18 15:40
 * @Description
 * @Version 1.0
 */

@Slf4j
public class CartServiceImplTest extends MallApplicationTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartServiceImpl cartServiceImpl;

    private Gson gson=new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void addCart() {
        CartAddForm cartAddForm=new CartAddForm();
        cartAddForm.setSelected(true);
        cartAddForm.setProductId(27);
        ResponseVo<CartVo> cartVoResponseVo = cartService.addCart(1, cartAddForm);
        log.info(gson.toJson(cartVoResponseVo)+"");
    }

    @Test
    public void list(){
        ResponseVo<CartVo> list = cartService.list(1);
        log.info(gson.toJson(list)+"");
    }

    @Test
    public void update(){
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setQuantity(2);
        cartUpdateForm.setSelected(false);
        ResponseVo<CartVo> update = cartService.update(1, 26, cartUpdateForm);
        log.info(gson.toJson(update)+"");
    }

    @Test
    public void delete(){
        ResponseVo<CartVo> delete = cartService.delete(1, 26);
        log.info(gson.toJson(delete)+"");
    }

    @Test
    public void testStream(){
        List<Cart> cartList = cartServiceImpl.listForCart(1);
        List<Cart> cartList1 = cartServiceImpl.listForCartTest(1);
        log.info(gson.toJson(cartList)+"");
        log.info(gson.toJson(cartList1)+"");

    }

    @Test
    public void selectAll() {
        ResponseVo<CartVo> cartVoResponseVo = cartService.selectAll(1);
        log.info(gson.toJson(cartVoResponseVo)+"----selectAll----");
    }

    @Test
    public void unSelectAll() {
        ResponseVo<CartVo> cartVoResponseVo = cartService.unSelectAll(1);
        log.info(gson.toJson(cartVoResponseVo)+"----unSelectAll----");
    }

    @Test
    public void sum() {
        ResponseVo<Integer> sum = cartService.sum(1);
        log.info(gson.toJson(sum)+"----sum----");

    }
}