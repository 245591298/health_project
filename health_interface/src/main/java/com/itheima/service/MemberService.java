package com.itheima.service;


import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Member;
import com.itheima.pojo.PycIllness;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MemberService
 * @Date 2020/3/2 23:37
 * @User 王骁
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);

    //xgw_健康干预分页查询
    PageResult XgwFindPage(QueryPageBean queryPageBean);

    PageResult zs_findPage(QueryPageBean queryPageBean);

}
