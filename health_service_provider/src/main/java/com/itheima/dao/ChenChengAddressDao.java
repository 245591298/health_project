package com.itheima.dao;

import com.itheima.pojo.ChenChengAddress;

import java.util.List;

public interface ChenChengAddressDao {
    //测试
    List<ChenChengAddress> findAll();

    String findById(Integer id);
}
