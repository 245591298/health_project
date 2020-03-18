package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;

public interface ZS_BodyListService {
    PageResult zs_findPage(QueryPageBean queryPageBean);
}
