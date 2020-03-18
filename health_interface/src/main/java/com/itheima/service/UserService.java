package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    //xgw_用户管理分页查询
    PageResult findPage(QueryPageBean queryPageBean);
    //xgw_用户管理新增用户
    void add(User user);
    //xgw_回显用户所对应的角色
    List<Integer> findRoleIdByUserId(Integer id);
    //xgw_增加用户角色
    void addRole(User user,Integer[] ids);
}
