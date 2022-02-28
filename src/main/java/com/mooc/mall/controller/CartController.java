package com.mooc.mall.controller;


import com.mooc.mall.consts.MallConst;
import com.mooc.mall.form.CartAddForm;
import com.mooc.mall.form.CartUpdateForm;
import com.mooc.mall.pojo.User;
import com.mooc.mall.service.CartService;
import com.mooc.mall.vo.CartVo;
import com.mooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @Author gaomy
 * @Date 2022/2/18 14:42
 * @Description
 * @Version 1.0
 */

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm, HttpSession httpSession){
        User user= (User)httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.addCart(user.getId(),cartAddForm);
    }

    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession httpSession){
        User user= (User)httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @PutMapping("/carts")
    public ResponseVo<CartVo> update(@Valid @RequestBody CartUpdateForm cartUpdateForm,Integer productId, HttpSession httpSession){
        User user= (User)httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.update(user.getId(),productId,cartUpdateForm);
    }

    @DeleteMapping("/carts")
    public ResponseVo<CartVo> delete(Integer productId, HttpSession httpSession){
        User user= (User)httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(user.getId(),productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll( HttpSession httpSession){
        User user= (User)httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unSelectAll( HttpSession httpSession){
        User user= (User)httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession httpSession){
        User user= (User)httpSession.getAttribute(MallConst.CURRENT_USER);
        return cartService.sum(user.getId());
    }




}
