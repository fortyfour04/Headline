package com.forty.controller;

import com.forty.pojo.User;
import com.forty.service.UserService;
import com.forty.utils.JwtHelper;
import com.forty.utils.Result;
import com.forty.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: FortyFour
 * @description:
 * @time: 2024/10/7 14:01
 * @version:
 */
@RestController //json格式文件读取
@RequestMapping("user")
@CrossOrigin //跨域访问
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    //用户登录 json格式文件读取
    @PostMapping("login")
    public Result login(@RequestBody User user) {
        Result result = userService.login(user);
        return result;
    }

    //token获取用户数据 请求头模式
    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token) {
        Result result = userService.getUserInfo(token);
        return result;
    }

    //用户名注册查重 param格式文件(默认)
    @PostMapping("checkUserName")
    public Result checkUserName(String username) {
        Result result = userService.checkUserName(username);
        return result;
    }

    //用户注册功能
    @PostMapping("regist")
    public Result regist(@RequestBody User user) {
        Result result = userService.regist(user);
        return result;
    }

    //验证登录信息token是否在有效期内
    @PostMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token) {
        boolean expiration = jwtHelper.isExpiration(token); //为1则超时
        if (expiration) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }

}
