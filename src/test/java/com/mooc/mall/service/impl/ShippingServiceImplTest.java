package com.mooc.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mooc.mall.MallApplicationTests;
import com.mooc.mall.service.ShippingService;
import com.mooc.mall.vo.ResponseVo;
import com.mooc.mall.form.ShippingForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


/**
 * @Author gaomy
 * @Date 2022/2/22 15:05
 * @Description
 * @Version 1.0
 */

@Slf4j
public class ShippingServiceImplTest extends MallApplicationTests {

    @Autowired
    private ShippingService shippingService;

    private Gson gson=new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void add() {
        ShippingForm shippingForm=new ShippingForm();
        shippingForm.setReceiverCity("北京市");
        shippingForm.setReceiverAddress("公司");
        shippingForm.setReceiverDistrict("海淀区");
        shippingForm.setReceiverMobile("13317156043");
        shippingForm.setReceiverName("高");
        shippingForm.setReceiverPhone("010123456");
        shippingForm.setReceiverProvince("北京市");
        shippingForm.setReceiverZip("000000");
        ResponseVo<Map> add = shippingService.add(1, shippingForm);
        log.info(gson.toJson(add));
    }

    @Test
    public void delete() {
        ResponseVo delete = shippingService.delete(1, 9);
        log.info(gson.toJson(delete));
    }

    @Test
    public void update() {
        ShippingForm shippingForm=new ShippingForm();
        shippingForm.setReceiverCity("天津市");
        shippingForm.setReceiverDistrict("河北区");
        shippingForm.setReceiverProvince("天津市");
        shippingForm.setReceiverZip("000000");
        ResponseVo update = shippingService.update(1, 9, shippingForm);
        log.info(gson.toJson(update));
    }

    @Test
    public void list() {
        ResponseVo<PageInfo> list = shippingService.list(1, 1, 10);
        log.info(gson.toJson(list));
    }
}