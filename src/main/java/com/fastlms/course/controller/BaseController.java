package com.fastlms.course.controller;

import com.fastlms.admin.util.PageUtil;

public class BaseController {
    public String getParamHtml(long totalCount, long pageSize, long pageIndex, String queryString) {

        PageUtil pageUtil = new PageUtil(totalCount, pageSize, pageIndex, queryString);
        return pageUtil.pager();

    }
}
