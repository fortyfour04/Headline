package com.forty.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forty.pojo.Headline;
import com.forty.pojo.vo.PortalVo;
import com.forty.service.HeadlineService;
import com.forty.mapper.HeadlineMapper;
import com.forty.utils.JwtHelper;
import com.forty.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 18140
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-10-06 23:33:23
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 首页数据查询业务
     * @param portalVo
     * @return Result
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {
        //由于页面接口返回所需的数据是以键值对列表存储的，故用map封装后放入page进行分页
        IPage<Headline> pages = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
        headlineMapper.selectMyPage(pages,portalVo);

        Map data = new HashMap();
        data.put("pageData",pages.getRecords());//整体数据
        data.put("pageNum",pages.getCurrent()); //当前页码
        data.put("pageSize",pages.getSize());   //单页容量
        data.put("totalPage",pages.getPages()); //总页数
        data.put("totalSize",pages.getTotal()); //总容量

        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo",data);

        return Result.ok(pageInfo);
    }

    /**
     * 点击“查看全文”后根据hid获取头条文章内容
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map data = headlineMapper.selectMyId(hid);
        Map headlineMap = new HashMap();
        headlineMap.put("headline", data);

        //实现每次点击阅读量加1,新建一个headline类后将访问量加1并修改version
        Headline headline = new Headline();
        headline.setHid(hid);
        //乐观锁利用version判断前后是否一致来决定是否修改数据以实现保护
        headline.setVersion((Integer)data.get("version"));
        headline.setPageViews((Integer)data.get("pageViews") + 1);
        headlineMapper.updateById(headline);

        return Result.ok(headlineMap);
    }

    /**
     * 发布头条业务
     * @param headline
     * @return
     */
    @Override
    public Result publish(Headline headline, String token) {
        //根据token查询uid
        int userId = jwtHelper.getUserId(token).intValue();

        //空缺数据填补
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        headlineMapper.insert(headline);

        return Result.ok(null);
    }

    /**
     * 修改头条内容 由于是修改操作，因此需要使用乐观锁判断对应的version是否一致
     * @param headline
     * @return
     */
    @Override
    public Result updateHeadline(Headline headline) {
        //根据hid查询最新的version
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version); //乐观锁
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);
        return Result.ok(null);
    }
}




