package com.mooc.mall.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mooc.mall.dao.ShippingMapper;
import com.mooc.mall.enums.ResponseEnum;
import com.mooc.mall.pojo.Shipping;
import com.mooc.mall.service.ShippingService;
import com.mooc.mall.vo.ResponseVo;
import com.mooc.mall.form.ShippingForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author gaomy
 * @Date 2022/2/22 14:30
 * @Description
 * @Version 1.0
 */

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVo<Map> add(Integer uid, ShippingForm shippingForm) {
        Shipping shipping=new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setUserId(uid);
        int row = shippingMapper.insertSelective(shipping);
        if (row==0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        Map<String,Integer> map=new HashMap<>();
        map.put("shippingId",shipping.getId());
        return ResponseVo.success(map);
    }

    @Override
    public ResponseVo delete(Integer uid, Integer shippingId) {
        int row = shippingMapper.deleteByIdAndUid(uid, shippingId);
        if (row==0){
            return ResponseVo.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }
        return ResponseVo.success("删除地址成功");
    }

    @Override
    public ResponseVo update(Integer uid, Integer shippingId, ShippingForm shippingForm) {
        Shipping shipping=new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setId(shippingId);
        shipping.setUserId(uid);
        int row = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (row==0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success("修改成功");
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUid(uid);
        PageInfo pageInfo=new PageInfo(shippingList);
        return ResponseVo.success(pageInfo);
    }
}
