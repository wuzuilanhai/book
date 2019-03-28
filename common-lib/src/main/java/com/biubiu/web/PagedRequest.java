package com.biubiu.web;

import lombok.Data;

/**
 * Created by Haibiao.Zhang on 2019-03-28 10:44
 */
@Data
public class PagedRequest {

    //第几页 从1开始 默认为1
    private Integer pageIndex = 1;

    //每页行数 默认为10
    private Integer pageSize = 10;

}
