package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.User;

import java.util.List;

import java.util.Map;

public interface UserDao {
    User findByUsername(String username);
    //xgw_用户管理分页查询
    Page<User> selectPageByCondition(String queryString);
    //xgw_用户管理新增用户
    void add(User user);
    //xgw_回显用户所对应的角色
    List<Integer> findRoleIdByUserId(Integer id);
    //xgw_根据用户id查询角色是否存在
    Long findRoleIdCountByUserId(Integer userId);
    //xgw_存在相应角色,删除
    void deleteRoleIdAll(Integer userId);
    //xgw_重新建立用户和角色中间表
    void addUserIdAndRoleId(Map<String, Integer> map);

    //User findByUsername(String username);

}
