package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MemberDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Illness;
import com.itheima.pojo.Member;
import com.itheima.pojo.User;
import com.itheima.pojo.XgwMemberDifference;
import com.itheima.pojo.Member;
import com.itheima.pojo.PycBody;
import com.itheima.pojo.PycIllness;
import com.itheima.pojo.PycPsychology;
import com.itheima.pojo.*;
import com.itheima.service.MemberService;
import com.itheima.utils.MD5Utils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MemberServiceImpl
 * @Date 2020/3/2 23:40
 * @User 王骁
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        if(member.getPassword() != null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    //xgw_健康干预分页查询
    @Override
    public PageResult XgwFindPage(QueryPageBean queryPageBean) {
        //取出页面数据
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //设置分页查询条件
        PageHelper.startPage(currentPage,pageSize);
        //将查询到的数据封装到PageResult中
        Page<XgwMemberDifference> userPage = memberDao.selectPageByCondition(queryString);
        List<XgwMemberDifference> result = userPage.getResult();
        for (XgwMemberDifference member : result) {
            String sex = member.getSex();
            //性别格式转换
            if(sex != null){
                if(sex.equals("1")){
                    member.setSex("男");
                }
                if(sex.equals("2")){
                    member.setSex("女");
                }
            }
        }
        return new PageResult(userPage.getTotal(),result);
    }

    @Override
    public PageResult zs_findPage(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<ZS_Psychology> page =memberDao.zs_findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }



}
