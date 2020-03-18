package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    //通过角色的id查询角色所拥有的所有的权限信息
    Set<Permission> findByid(int id);
}
