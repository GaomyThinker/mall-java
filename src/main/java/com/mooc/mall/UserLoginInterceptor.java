package com.mooc.mall;


import com.mooc.mall.consts.MallConst;
import com.mooc.mall.exception.UserLoginException;
import com.mooc.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author gaomy
 * @Date 2022/1/27 10:26
 * @Description
 * @Version 1.0
 */

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    /**
     *true 表示继续流程  false表示中断
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("preHandle...");
        User user = (User)request.getSession().getAttribute(MallConst.CURRENT_USER);
        if (null==user){
            log.info("user is null");
//            response.getWriter().print("error");
            throw new UserLoginException();
//            return false;
//            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }
        return true;
    }
}
