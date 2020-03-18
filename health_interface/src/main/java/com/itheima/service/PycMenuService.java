package com.itheima.service;

import com.fasterxml.jackson.core.SerializableString;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Map;

/**
 * @author pyc
 */
public interface PycMenuService {

    List<Map<String,Object>> PycfindMenuByRoleId(String username);

    PageResult pycFindPage(QueryPageBean queryPageBean);

    void pycAddmenu(Menu menu);

    Menu pycFindMenuById(Integer id);

    void pycEditMenu(Menu menu);
}
