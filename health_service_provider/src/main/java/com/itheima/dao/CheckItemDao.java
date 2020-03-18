package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    /**
    * 添加检查项
    */
    void add(CheckItem checkItem);
    /**
     * 查询检查项
     */
    Page<CheckItem> selectByCondition(String queryString);

    /**
     * 删除检查项
     */
    void deleteById(Integer id);

    /**
     * 删除前先查询检查项是否在检查组中
     */
    long findCountByCheckItemId(Integer checkItemId);

    /**
     * 通过id查询检查项的数据用于回显
     */
    CheckItem findById(Integer id);

    /**
     * 更改检查项的信息
     */
    void edit(CheckItem checkItem);

    /**
     * 查询所有的检查项
     */
    List<CheckItem> findAll();

}
