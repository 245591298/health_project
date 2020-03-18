package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.JianjiahaoAddressDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.ChenChengAddress;
import com.itheima.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(interfaceClass = AddressService.class)
public class AddressServiceImpl implements AddressService {
    @Autowired
    private JianjiahaoAddressDao jianjiahaoAddressDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        String condition = queryPageBean.getQueryString();
        Integer pageSize = queryPageBean.getPageSize();
        PageHelper.startPage(currentPage,pageSize);
        Page<ChenChengAddress> page = jianjiahaoAddressDao.findPage(condition);
        return new PageResult(page.getTotal(),page);
    }

    @Override
    public void addAddress(ChenChengAddress chenChengAddress)
    {
        jianjiahaoAddressDao.add(chenChengAddress);
    }

    @Override
    public void editAddress(ChenChengAddress chenChengAddress) {
        jianjiahaoAddressDao.update(chenChengAddress);
    }

    @Override
    public ChenChengAddress findById(Integer id) {
        return jianjiahaoAddressDao.findById(id);
    }

    @Override
    public void delAddress(Integer id) {
        jianjiahaoAddressDao.delAddressAndOrder(id);
        jianjiahaoAddressDao.del(id);
    }
}
