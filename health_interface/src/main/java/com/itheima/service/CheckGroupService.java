package com.itheima.service;

import com.itheima.entity.PageResult;

import com.itheima.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组服务接口
 */
public interface CheckGroupService {
    //添加检查组
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    //查询检查组分页展示
    PageResult pageQuery(Integer currentPage ,Integer pageSize ,String queryString);

    //通过id查询检查组,进行数据回显
    CheckGroup findById(Integer id);

    //修改检查组的数据
    void edit(CheckGroup checkGroup , Integer[] checkitemIds);

    //查询检查组对应的检查项
    List<Integer> findCheckitemsByCheckGroupId(Integer id);

    //删除检查组
    void delete(Integer id);

    //查询所有检查组
    List<CheckGroup> findAll();
}
