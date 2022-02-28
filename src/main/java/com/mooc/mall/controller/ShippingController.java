package com.mooc.mall.controller;


import com.mooc.mall.consts.MallConst;
import com.mooc.mall.form.ShippingForm;
import com.mooc.mall.pojo.User;
import com.mooc.mall.service.ShippingService;
import com.mooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @Author gaomy
 * @Date 2022/2/22 16:10
 * @Description
 * @Version 1.0
 */

@RestController
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping("/shippings")
    public ResponseVo add(@Valid @RequestBody ShippingForm shippingForm, HttpSession httpSession){
        User user =(User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return shippingService.add(user.getId(),shippingForm);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId,HttpSession httpSession){
        User user =(User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return shippingService.delete(user.getId(),shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVo update(@PathVariable Integer shippingId,
                             @Valid @RequestBody ShippingForm shippingForm,
                             HttpSession httpSession){
        User user =(User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return shippingService.update(user.getId(), shippingId,shippingForm);
    }


    @GetMapping("/shippings")
    public ResponseVo list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                           HttpSession httpSession){
        User user =(User) httpSession.getAttribute(MallConst.CURRENT_USER);
        return shippingService.list(user.getId(),pageNum,pageSize);
    }
}
