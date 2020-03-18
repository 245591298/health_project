package com.itheima.service;

import com.itheima.pojo.ChenChengAddress;

import java.util.List;

public interface ChenChengAddressService {
    //测试
    List<ChenChengAddress> findAll();

    String findById(Integer id);
}
