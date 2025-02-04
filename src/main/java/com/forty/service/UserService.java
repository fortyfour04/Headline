package com.forty.service;

import com.forty.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forty.utils.Result;

/**
* @author 18140
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-10-06 23:33:24
*/
public interface UserService extends IService<User> {
    Result login(User user);

    Result getUserInfo(String token);

    Result checkUserName(String username);

    Result regist(User user);
}
