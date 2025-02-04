package com.forty.service;

import com.forty.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forty.pojo.vo.PortalVo;
import com.forty.utils.Result;

/**
* @author 18140
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-10-06 23:33:23
*/
public interface HeadlineService extends IService<Headline> {

    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline, String token);

    Result updateHeadline(Headline headline);
}
