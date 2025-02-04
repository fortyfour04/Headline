package com.forty.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forty.service.UserService;
import com.forty.utils.JwtHelper;
import com.forty.utils.Result;
import com.forty.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Request;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author: FortyFour
 * @description: 登录保护拦截器，发布、修改头条之前验证登录是否有效
 * @time: 2024/10/9 16:02
 * @version:
 */
@Component
public class LoginProtectInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //从请求头中获取token
        String token = request.getHeader("token");
        //检查是否有效
        boolean expiration = jwtHelper.isExpiration(token);
        //若有效则直接返回
        if (!expiration) {
            return true;
        }

        //若无效则返回对应的504 json数据
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
        ObjectMapper mapper = new ObjectMapper();       //jackson提供的json与java对象之间的转换
        String s = mapper.writeValueAsString(result);   //将result转为字符串
        response.getWriter().print(s);

        return false;
    }
}
