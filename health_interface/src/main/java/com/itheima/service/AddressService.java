package com.itheima.service;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.ChenChengAddress;

public interface AddressService {
    public PageResult findPage(QueryPageBean queryPageBean);
    public void addAddress(ChenChengAddress chenChengAddress);
    public void delAddress(Integer id);
    public void editAddress(ChenChengAddress chenChengAddress);

    ChenChengAddress findById(Integer id);
}
