package com.mooc.mall.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.mooc.mall.enums.ResponseEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * @Author gaomy
 * @Date 2022/1/4 15:44
 * @Description
 * @Version 1.0
 */

@Data
//@JsonSerialize
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {

    private Integer status;

    private String msg;

    private T data;

    public ResponseVo(Integer status, String msg){
        this.status=status;
        this.msg=msg;
    }

    public ResponseVo(Integer status, T data){
        this.status=status;
        this.data=data;
    }

    public static <T>ResponseVo<T> success(){
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getDesc());
    }

    public static <T>ResponseVo<T> successByMsg(String msg){
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(),msg);
    }

    public static <T>ResponseVo<T> success(T data){
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(),data);
    }

    public static <T>ResponseVo<T> error(ResponseEnum responseEnum){
        return new ResponseVo<>(responseEnum.getCode(),responseEnum.getDesc());
    }

    public static <T>ResponseVo<T> error(ResponseEnum responseEnum,String message){
        return new ResponseVo<>(responseEnum.getCode(),message);
    }

    public static <T>ResponseVo<T> error(ResponseEnum responseEnum, BindingResult bindingResult){
        return new ResponseVo<>(responseEnum.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getField()+""+bindingResult.getFieldError().getDefaultMessage());
    }

}
