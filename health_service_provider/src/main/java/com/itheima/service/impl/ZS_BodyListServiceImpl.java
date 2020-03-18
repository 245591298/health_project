package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.BodyListDao;
import com.itheima.dao.MemberDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.ZS_BodyList;
import com.itheima.pojo.ZS_Psychology;
import com.itheima.service.ZS_BodyListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = ZS_BodyListService.class)
@Transactional
public class ZS_BodyListServiceImpl implements ZS_BodyListService {
    @Autowired
    private MemberDao memberDao;
    @Override
    public PageResult zs_findPage(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<ZS_BodyList> page =memberDao.zs_findBodyByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
