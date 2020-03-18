package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.ZS_BodyList;

public interface BodyListDao {
    Page<ZS_BodyList> zs_findByCondition(String queryString);
}
