package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Menu;

import java.util.List;

public interface PycMenuDao {

    List<Menu> findMenuByUsername(String username);

    Page<Menu> pycSelectByCondtion(String queryString);

    void pycAddMenu(Menu menu);

    Menu pycFindById(Integer id);

    void pycEditMenu(Menu menu);
}
