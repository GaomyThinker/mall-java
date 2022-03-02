package com.mooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mooc.mall.MallApplicationTests;
import com.mooc.mall.vo.OrderVo;
import com.mooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author gaomy
 * @Date 2022/2/23 14:40
 * @Description
 * @Version 1.0
 */

@Slf4j
@Transactional
public class OrderServiceImplTest extends MallApplicationTests {

    private Gson gson=new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        ResponseVo<OrderVo> orderVoResponseVo = orderService.create(1, 4);
        log.info("------------------------------------");
        log.info(gson.toJson(orderVoResponseVo));
    }

    @Test
    public void list(){
        ResponseVo<PageInfo> list = orderService.list(1, 1, 4);
        log.info("{}",gson.toJson(list));
    }

    @Test
    public void detail(){
        ResponseVo<OrderVo> detail = orderService.detail(1, 1645771848777L);
        log.info("{}",gson.toJson(detail));
    }
    @Test
    public void cancel(){
        ResponseVo cancel = orderService.cancel(1, 1645771848777L);
        log.info("{}",gson.toJson(cancel));
    }
}