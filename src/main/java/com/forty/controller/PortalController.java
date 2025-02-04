package com.forty.controller;

import com.forty.pojo.vo.PortalVo;
import com.forty.service.HeadlineService;
import com.forty.service.TypeService;
import com.forty.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: FortyFour
 * @description: 首页控制器
 * @time: 2024/10/8 13:14
 * @version:
 */
@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private HeadlineService headlineService;

    //获取所有头条类型
    @GetMapping("findAllTypes")
    public Result findAllTypes() {
        Result result = typeService.findAllTypes();
        return result;
    }

    //根据关键字和分页等进行首页数据查询
    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo) {
        Result result = headlineService.findNewsPage(portalVo);
        return result;
    }

    //查询头条详情
    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid) {
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }
}
