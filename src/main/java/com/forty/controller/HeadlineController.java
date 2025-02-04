package com.forty.controller;

import com.forty.pojo.Headline;
import com.forty.service.HeadlineService;
import com.forty.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: FortyFour
 * @description: 头条发布、修改等控制器
 * @time: 2024/10/9 15:44
 * @version:
 */
@RestController
@RequestMapping("headline")
@CrossOrigin
//由于对头条的操作都要求登陆后才能进行，故添加拦截器实现token验证
public class HeadlineController {

    @Autowired
    private HeadlineService headlineService;

    //头条发布  需要同时获取header的token以获取uid来确定发布者
    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline,@RequestHeader String token) {
        Result result = headlineService.publish(headline,token);
        return result;
    }

    //头条回显(在修改之前先根据hid查询数据并展示在修改界面)
    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid) {
        //通过服务层增强直接获取对应的数据，省去mapper层的查询
        Headline byId = headlineService.getById(hid);
        Map data = new HashMap();
        data.put("headline", byId);
        return Result.ok(data);
    }

    //头条修改 需要注意version的获取和updateTime的更新
    @PostMapping("update")
    public Result update(@RequestBody Headline headline) {
        Result result = headlineService.updateHeadline(headline);
        return result;
    }

    //头条删除
    @PostMapping("removeById")
    public Result removeById(Integer hid) {
        headlineService.removeById(hid);
        return Result.ok(null);
    }
}
