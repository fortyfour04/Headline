package com.forty.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forty.pojo.Type;
import com.forty.service.TypeService;
import com.forty.mapper.TypeMapper;
import com.forty.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 18140
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-10-06 23:33:23
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    /**
     * 获取所有首页类别
     * @return Result
     */
    @Override
    public Result findAllTypes() {
        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);
    }
}




