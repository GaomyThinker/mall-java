package com.mooc.mall.controller;


import com.github.pagehelper.PageInfo;
import com.mooc.mall.consts.MallConst;
import com.mooc.mall.form.OrderCreateForm;
import com.mooc.mall.pojo.User;
import com.mooc.mall.service.OrderService;
import com.mooc.mall.vo.OrderVo;
import com.mooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @Author gaomy
 * @Date 2022/3/1 15:24
 * @Description
 * @Version 1.0
 */

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm orderCreateForm,
                                      HttpSession httpSession){
        User user = (User)httpSession.getAttribute(MallConst.CURRENT_USER);
       return orderService.create(user.getId(), orderCreateForm.getShippingId());
    }


    @GetMapping("/orders")
    public ResponseVo<PageInfo> list(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     HttpSession httpSession){
        User user = (User)httpSession.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(user.getId(),pageNum,pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(@PathVariable Long orderNo,
                                      HttpSession httpSession){
        User user = (User)httpSession.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(), orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(@PathVariable Long orderNo,
                                      HttpSession httpSession){
        User user = (User)httpSession.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(), orderNo);
    }
}
