package com.mooc.mall.service;


import com.mooc.mall.form.CartAddForm;
import com.mooc.mall.form.CartUpdateForm;
import com.mooc.mall.pojo.Cart;
import com.mooc.mall.vo.CartVo;
import com.mooc.mall.vo.ResponseVo;

import java.util.List;

/**
 * @Author gaomy
 * @Date 2022/2/18 15:19
 * @Description
 * @Version 1.0
 */

public interface CartService {
    ResponseVo<CartVo> addCart(Integer uid, CartAddForm cartAddForm);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    ResponseVo<CartVo> delete(Integer uid,Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unSelectAll(Integer uid);

    ResponseVo<Integer> sum(Integer uid);

    List<Cart> listForCart(Integer uid);
}
