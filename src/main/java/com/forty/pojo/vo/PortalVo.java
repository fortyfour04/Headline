package com.forty.pojo.vo;

import lombok.Data;

/**
 * @author: FortyFour
 * @description: PortalViewObject首页显示层对象，用于定义首页需求返回的特殊结构
 * @time: 2024/10/8 15:11
 * @version:
 */
@Data
public class PortalVo {

    private String keyWords;

    private Integer type;

    private Integer pageNum;

    private Integer pageSize;

}
