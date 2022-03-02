package com.mooc.mall.service;


import com.github.pagehelper.PageInfo;
import com.mooc.mall.vo.OrderVo;
import com.mooc.mall.vo.ResponseVo;

/**
 * @Author gaomy
 * @Date 2022/2/23 10:13
 * @Description
 * @Version 1.0
 */


public interface OrderService {
    // 创建订单
    ResponseVo<OrderVo> create(Integer uid,Integer shipping);

    // 订单列表
    ResponseVo<PageInfo> list(Integer uid,Integer pageNum,Integer pageSize);

    // 订单详情
    ResponseVo<OrderVo> detail(Integer uid,Long orderNo);

    // 取消订单
    ResponseVo cancel(Integer uid,Long orderNo);

    // 修改订单状态
    void paid(Long orderNo);
}
