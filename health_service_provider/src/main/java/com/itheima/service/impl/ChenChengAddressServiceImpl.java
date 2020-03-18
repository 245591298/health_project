package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.ChenChengAddressDao;
import com.itheima.pojo.ChenChengAddress;
import com.itheima.service.ChenChengAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service(interfaceClass = ChenChengAddressService.class)
@Transactional
public class ChenChengAddressServiceImpl implements ChenChengAddressService {
    @Autowired
    private ChenChengAddressDao chenChengAddressDao;
    @Override//查询所有的体检机构 测试
    public List<ChenChengAddress> findAll() {
        return chenChengAddressDao.findAll();
    }

    @Override//根据id查询体检机构
    public String findById(Integer id) {
        return chenChengAddressDao.findById(id);
    }
}
