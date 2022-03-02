package com.mooc.mall.service;


import com.github.pagehelper.PageInfo;
import com.mooc.mall.form.ShippingForm;
import com.mooc.mall.vo.ResponseVo;

import java.util.Map;

/**
 * @Author gaomy
 * @Date 2022/2/22 14:30
 * @Description
 * @Version 1.0
 */


public interface ShippingService {

    ResponseVo<Map> add(Integer uid, ShippingForm shippingForm);

    ResponseVo delete(Integer uid, Integer shippingId);

    ResponseVo update(Integer uid,Integer shippingId, ShippingForm shippingForm);

    ResponseVo<PageInfo> list(Integer uid,Integer pageNum,Integer pageSize);

}
