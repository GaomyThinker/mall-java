package com.mooc.mall.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.mooc.mall.enums.ResponseEnum;
import lombok.Data;

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

    public static <T>ResponseVo<T> success(){
        return new ResponseVo<>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getDesc());
    }

    public static <T>ResponseVo<T> error(ResponseEnum responseEnum){
        return new ResponseVo<>(responseEnum.getCode(),responseEnum.getDesc());
    }
}
