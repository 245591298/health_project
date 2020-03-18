package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.ChenChengAddress;

public interface JianjiahaoAddressDao {
    public Page<ChenChengAddress> findPage(String condition);
    public void add(ChenChengAddress chenChengAddress);
    public void update(ChenChengAddress chenChengAddress);
    public void del(Integer id);
    public void delAddressAndOrder(Integer id);

    ChenChengAddress findById(Integer id);
}
