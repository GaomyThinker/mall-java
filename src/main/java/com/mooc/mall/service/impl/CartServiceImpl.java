package com.mooc.mall.service.impl;


import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
import com.mooc.mall.dao.ProductMapper;
import com.mooc.mall.enums.ProductStatusEnum;
import com.mooc.mall.enums.ResponseEnum;
import com.mooc.mall.form.CartAddForm;
import com.mooc.mall.form.CartUpdateForm;
import com.mooc.mall.pojo.Cart;
import com.mooc.mall.pojo.Product;
import com.mooc.mall.service.CartService;
import com.mooc.mall.vo.CartProductVo;
import com.mooc.mall.vo.CartVo;
import com.mooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author gaomy
 * @Date 2022/2/18 15:21
 * @Description
 * @Version 1.0
 */

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private final static String CART_REDIS_KEY_TEMPLATE="cart_%d";

    private Gson gson=new Gson();

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public ResponseVo<CartVo> addCart(Integer uid, CartAddForm cartAddForm) {
        // 添加到购物车的数量，每次都添加一个
        Integer quantity=1;
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        // 商品是否存在
        if ( product== null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIT);
        }
        // 商品是否在正常销售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        // 商品库存是否充足
        if (product.getStock()<=0){
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        // 写入redis
        // key: cart_1
        // 如果这样写会进行一轮一轮的覆盖
//        redisTemplate.opsForValue().set(String.format(CART_REDIS_KEY_TEMPLATE,uid),
//                gson.toJson(new Cart(product.getId(),quantity,cartAddForm.getSelected())));

        HashOperations<String,String,String> opsForValue=redisTemplate.opsForHash();
        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);
        String value=opsForValue.get(redisKey,String.valueOf(product.getId()));
        Cart cart;
        if (StringUtil.isEmpty(value)){
            // 没有该商品，新增
            cart=new Cart(product.getId(),quantity,cartAddForm.getSelected());
        }else {
            // 已经存在，数量+1
            cart=gson.fromJson(value,Cart.class);
            cart.setQuantity(cart.getQuantity()+quantity);
        }

        opsForValue.put(redisKey,String.valueOf(product.getId()),gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        // 从redis查到数据
        HashOperations<String,String,String> opsForValue=redisTemplate.opsForHash();
        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);

        Map<String, String> entries = opsForValue.entries(redisKey);
        // entries 的值为 {27={"productId":27,"quantity":2,"productSelected":true}, 26={"productId":26,"quantity":1,"productSelected":true}}
        log.info(entries+"---------------------------------------");

        boolean selectAll=true;
        Integer cartTotalQuantity=0;
        BigDecimal cartTotalPrice=BigDecimal.ZERO;
        CartVo cartVo=new CartVo();
        List<CartProductVo> cartProductVoList=new ArrayList<>();
        for (Map.Entry<String,String> entry: entries.entrySet()) {
            // productId
            Integer productId =Integer.valueOf(entry.getKey()) ;
            // 得到cart对象
            Cart cart=gson.fromJson(entry.getValue(), Cart.class);
            log.info("cart{}"+cart);
            // @TODO 需要优化，使用mysql里的in 不要在for循环中查
            Product product=productMapper.selectByPrimaryKey(productId);
            // 对数据进行封装
            if (product!=null){
                CartProductVo cartProductVo=new CartProductVo(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected());
                cartProductVoList.add(cartProductVo);

                // 有任何一个购物车中的商品为未选中，结果就为未选中
                if (!cart.getProductSelected()){
                    selectAll=false;
                }
                // 只计算选中的总价
                if (cart.getProductSelected()){
                    cartTotalPrice= cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }

            }
            cartTotalQuantity+=cart.getQuantity();
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setSelectAll(selectAll);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        HashOperations<String,String,String> opsForValue=redisTemplate.opsForHash();
        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);
        String value=opsForValue.get(redisKey,String.valueOf(productId));


        if (StringUtil.isEmpty(value)){
            // 没有该商品，报错
                return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIT);
        }
        Cart cart=gson.fromJson(value,Cart.class);
            // 已经存在，修改内容
            if (cartUpdateForm.getSelected()!=null){
                cart.setProductSelected(cartUpdateForm.getSelected());
            }
            if (cartUpdateForm.getQuantity()!=null && cartUpdateForm.getQuantity()>=0){
                cart.setQuantity(cartUpdateForm.getQuantity());
            }



        opsForValue.put(redisKey,String.valueOf(productId),gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String,String,String> opsForValue=redisTemplate.opsForHash();
        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);
        String value=opsForValue.get(redisKey,String.valueOf(productId));


        if (StringUtil.isEmpty(value)){
            // 没有该商品，报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIT);
        }
        // 已经存在，删除
        opsForValue.delete(redisKey,String.valueOf(productId));
        return list(uid);
    }

    // 全选购物车中的所有商品
    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String,String,String> opsForValue=redisTemplate.opsForHash();
        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);
        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            cart.setProductSelected(true);
            opsForValue.put(redisKey,String.valueOf(cart.getProductId()),gson.toJson(cart));
        }
        return list(uid);
    }

    // 把购物车中商品的状态都改为未选中
    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String,String,String> opsForValue=redisTemplate.opsForHash();
        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);
        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            cart.setProductSelected(false);
            opsForValue.put(redisKey,String.valueOf(cart.getProductId()),gson.toJson(cart));
        }
        return list(uid);
    }


    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        // 对Cart的quantity进行从0开始的累加和
        Integer sum=listForCart(uid).stream()
                .map(Cart::getQuantity)
                .reduce(0,Integer::sum);
        return ResponseVo.success(sum);
    }


    // 把获取购物车中所有的Cart对象抽象为一个共同方法
    public List<Cart> listForCart(Integer uid){
        HashOperations<String,String,String> opsForValue=redisTemplate.opsForHash();
        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);

        List<Cart> cartList=new ArrayList<>();
        Map<String, String> entries = opsForValue.entries(redisKey);

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartList.add(gson.fromJson(entry.getValue(),Cart.class));
        }
        return  cartList;
    }

    // 抽象为共同类的另一种写法
    public List<Cart> listForCartTest(Integer uid){
        HashOperations<String,String,String> opsForValue=redisTemplate.opsForHash();
        String redisKey=String.format(CART_REDIS_KEY_TEMPLATE,uid);

        Map<String, String> entries = opsForValue.entries(redisKey);
        List<Cart> collect = entries.values().stream().map(e -> {
                    return gson.fromJson(e, Cart.class);
                })
                .collect(Collectors.toList());
        return  collect;
    }

}
