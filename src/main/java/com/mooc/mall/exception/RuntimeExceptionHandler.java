package com.mooc.mall.exception;


import com.mooc.mall.enums.ResponseEnum;
import com.mooc.mall.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.mooc.mall.enums.ResponseEnum.ERROR;

/**
 * @Author gaomy
 * @Date 2022/1/20 11:15
 * @Description
 * @Version 1.0
 */

@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.FORBIDDEN)  补充知识，返回的http状态和返回的内容中的状态没有关系
    public ResponseVo handle(RuntimeException runtimeException){
        return ResponseVo.error(ERROR,runtimeException.getMessage());
    }


    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo userLoginHandle(){
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }

}
