package com.forty.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.forty.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forty.pojo.vo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author 18140
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-10-06 23:33:23
* @Entity com.forty.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    IPage<Map> selectMyPage(IPage<Headline> page, @Param("portalVo") PortalVo portalVo);

    Map selectMyId(Integer hid);
}




