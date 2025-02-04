package com.forty.service;

import com.forty.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forty.utils.Result;

import java.util.List;

/**
* @author 18140
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-10-06 23:33:23
*/
public interface TypeService extends IService<Type> {

    Result findAllTypes();

}
