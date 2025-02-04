package com.forty.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forty.pojo.User;
import com.forty.service.UserService;
import com.forty.mapper.UserMapper;
import com.forty.utils.JwtHelper;
import com.forty.utils.MD5Util;
import com.forty.utils.Result;
import com.forty.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author 18140
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-10-06 23:33:24
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 登录业务
     * @param user
     * @return Result
     */
    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User DbUser = userMapper.selectOne(queryWrapper);

        //没有该用户账号
        if(DbUser == null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //密码比对
        if (!StringUtils.isEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(DbUser.getUserPwd())) {
            //密码正确，登录成功
            //根据id生成对应token，将token封装到result后返回
            String token = jwtHelper.createToken(Long.valueOf(DbUser.getUid()));
            Map data = new HashMap();
            data.put("token", token);
            return Result.ok(data);
        }

        //密码错误
        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);

    }

    /**
     * 用户信息获取
     * @param token
     * @return Result
     */
    @Override
    public Result getUserInfo(String token) {
        //判断token是否在有效期内,若有效则为false,若无效则为true
        boolean expiration = jwtHelper.isExpiration(token);

        if (expiration) {
            //token失效
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

        //获取用户对应数据,并将pwd置空保护密码安全
        int userId = jwtHelper.getUserId(token).intValue();
        User Dbuser = userMapper.selectById(userId);
        Dbuser.setUserPwd("");

        //将对应的用户数据封装后返回
        Map data = new HashMap();
        data.put("user", Dbuser);
        return Result.ok(data);
    }


    /**
     * 注册账户查重
     * @param username
     * @return Result
     */
    @Override
    public Result checkUserName(String username) {
        //判断账户名是否为空
        if (username == null || username.isEmpty()) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);

        //根据数据库中的查找结果进行判断，若找到的值大于0则代表已有账号
        if (userMapper.selectCount(queryWrapper) > 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        return Result.ok(null);
    }

    /**
     * 用户注册业务
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUsername,user.getUsername());

        //根据数据库中的查找结果进行判断，若找到的值大于0则代表已有账号
        if (userMapper.selectCount(queryWrapper) > 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }

        //密码生成
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        userMapper.insert(user);

        return Result.ok(null);
    }
}




