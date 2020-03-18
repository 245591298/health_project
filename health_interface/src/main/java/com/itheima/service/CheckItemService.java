package com.itheima.service;


import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * 检查项服务接口
 */
public interface CheckItemService {
    //添加检查项
    void add(CheckItem checkItem);

    //查询满足条件的检查项目
    PageResult pageQuery(QueryPageBean queryPageBean);

    //删除选中的检查项
    void deleteById(Integer id);

    //查询数据回显
    CheckItem findById(Integer id);

    //修改检查项的信息
    void edit(CheckItem checkItem);

    //查询所有检查项
    List<CheckItem> findAll();
}
