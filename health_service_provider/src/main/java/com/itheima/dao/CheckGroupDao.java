package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

/**
 * 持久层Dao接口
 */
public interface CheckGroupDao {
    //添加检查项
    void add(CheckGroup checkGroup);

    //操作中间表设置检查组和检查项的关系
    void setCheckGroupAndCheckItem(Map map);

    //根据条件查询
    Page<CheckGroup> selectByCondition(String queryString);

    //根据id查询检查组
    CheckGroup findById(Integer id);

    //修改检查组表的基本数据
    void editCheckGroup(CheckGroup checkGroup);

    //修改检查组表要先删除有关于检查组的所有检查项再重新添加关系
    void deleteAssociation(Integer id);

    //根据检查组id查询关联的检查项的id
    List<Integer> findCheckitemsByCheckGroupId(Integer id);

    //在中间表删除检查组对与其对应的检查项
    void deleteCheckGroupIdAndCheckItemIds(Integer id);

    //删除检查组
    void deleteCheckGroup(Integer id);

    //查询所有检查组
    List<CheckGroup> findAll();



}
