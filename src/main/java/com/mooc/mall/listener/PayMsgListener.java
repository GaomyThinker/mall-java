package com.mooc.mall.listener;


import com.google.gson.Gson;
import com.mooc.mall.pojo.PayInfo;
import com.mooc.mall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author gaomy
 * @Date 2022/3/1 15:59
 * @Description
 * @Version 1.0
 */

/**
 * 关于payInfo，正确姿势：pay项目提供client.jar,mall项目引入jar包
 */
@Component
@Slf4j
@RabbitListener(queues = "payNotify")
public class PayMsgListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void process(String msg){
        log.info("【接收到消息】=> {}",msg);

        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        if (payInfo.getPlatformStatus().equals("SUCCESS")){
            // 修改订单信息
            orderService.paid(payInfo.getOrderNo());
        }
    }
}
