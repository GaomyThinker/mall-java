package com.mooc.mall.service.impl;


import com.github.pagehelper.PageInfo;
import com.mooc.mall.dao.OrderItemMapper;
import com.mooc.mall.dao.OrderMapper;
import com.mooc.mall.dao.ProductMapper;
import com.mooc.mall.dao.ShippingMapper;
import com.mooc.mall.enums.OrderStatusEnum;
import com.mooc.mall.enums.PaymentTypeEnum;
import com.mooc.mall.enums.ProductStatusEnum;
import com.mooc.mall.enums.ResponseEnum;
import com.mooc.mall.pojo.*;
import com.mooc.mall.service.CartService;
import com.mooc.mall.service.OrderService;
import com.mooc.mall.vo.OrderItemVo;
import com.mooc.mall.vo.OrderVo;
import com.mooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author gaomy
 * @Date 2022/2/23 10:14
 * @Description
 * @Version 1.0
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        // 收货地址校验
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if (shipping==null){
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIT);
        }
        //获取购物车，校验（是否有商品，库存）
        List<Cart> cartList=cartService.listForCart(uid).stream()
                                       .filter(Cart::getProductSelected)
                                       .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cartList)){
            return ResponseVo.error(ResponseEnum.CAR_SELECTED_ID_EMPTY);
        }
             // 优化：获取cartList里面的productId 以避免在for循环中一遍一遍根据productId查询数据库
        Set<Integer> productIdSet = cartList.stream()
                                       .map(Cart::getProductId)
                                       .collect(Collectors.toSet());
        List<Product> products = productMapper.selectByProductIdSet(productIdSet);
             // 下一步就是对比购物车中的商品和数据库中查到的商品，看是否真是存在
             // 把List转换为map方便下面的比较
        Map<Integer,Product> map=products.stream().collect(Collectors.toMap(Product::getId,product->product));
        List<OrderItem> orderItemList=new ArrayList<>();
        Long orderNo=generatorOrderNo();
        for (Cart cart : cartList) {
            Product product = map.get(cart.getProductId());
            // 商品是否存在
            if (product==null){
                return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIT,"商品不存在 productId="+cart.getProductId());
            }
            // 商品上下架状态
            if (!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())){
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,"商品不是在售状态"+product.getName());
            }
            // 库存是否充足
            if (product.getStock()<cart.getQuantity()){
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR,"库存不正确"+product.getName());
            }

            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
            orderItemList.add(orderItem);
            // 减库存
            product.setStock(product.getStock()-cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if (row<=0){
                return ResponseVo.error(ResponseEnum.ERROR);
            }

        }

        // 计算总价，只计算选中的商品
        // 生成订单，入库：order和orderItem表，事务
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        int rowForOrder = orderMapper.insertSelective(order);
        if (rowForOrder<=0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
        if (rowForOrderItem<=0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        // 更新购物车（把选中商品删除）
        // redis有事务(打包命令)，redis不能回滚,所以更新购物车要写在所有操作没有问题之后
        for (Cart cart : cartList) {
            cartService.delete(uid, cart.getProductId());
        }

        // 构造orderVo
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);

        return ResponseVo.success(orderVo);
    }


    /**
     * 构建orderVo
     * @param order
     * @param orderItemList
     * @param shipping
     * @return
     */
    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVo orderVo=new OrderVo();
        BeanUtils.copyProperties(order,orderVo);

        List<OrderItemVo> orderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());

        orderVo.setOrderItemVoList(orderItemVoList);
        orderVo.setShippingId(shipping.getId());
        orderVo.setShippingVo(shipping);
        return orderVo;
    }

    /**
     * 生成订单，准备插入数据库
     * @param uid
     * @param orderNo
     * @param shippingId
     * @param orderItemList
     * @return
     */
    private Order buildOrder(Integer uid,Long orderNo,Integer shippingId,List<OrderItem> orderItemList){
        Order order=new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        BigDecimal payment = orderItemList.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());

        return order;
    }


    /**
     * 构建orderItem，准备插入数据库
     * @param uid
     * @param orderNo
     * @param quantity
     * @param product
     * @return
     */
    private OrderItem buildOrderItem(Integer uid,Long orderNo,Integer quantity,Product product){
        OrderItem orderItem=new OrderItem();
        orderItem.setUserId(uid);
        orderItem.setOrderNo(orderNo);
        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getName());
        orderItem.setProductImage(product.getMainImage());
        orderItem.setCurrentUnitPrice(product.getPrice());
        orderItem.setQuantity(quantity);
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return orderItem;
    }


    /**
     * 订单列表
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {

        return null;
    }

    /**
     * 订单详情
     * @param uid 传入uid，是保证只能查自己的订单
     * @param orderId
     * @return
     */
    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Integer orderId) {
        return null;
    }


    // 企业级：分布式唯一id 现在生成的是随机定的
    private Long generatorOrderNo(){
        return System.currentTimeMillis()+new Random().nextInt(999);
    }
}
