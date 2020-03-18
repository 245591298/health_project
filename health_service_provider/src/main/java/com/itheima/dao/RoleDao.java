package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    //通过用户的id查询用户具有的所有角色的信息
    Set<Role> findById(int id);
    //xgw_查询所有角色
    List<Role> findAll();
}
