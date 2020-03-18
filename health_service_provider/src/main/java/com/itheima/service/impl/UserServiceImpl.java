package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.UserDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Date 2020/3/3 12:55
 * @User 王骁
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    //xgw_用户管理分页查询
    @Override
    public PageResult findPage(QueryPageBean queryPageBean){
        //取出页面数据
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //设置分页查询条件
        PageHelper.startPage(currentPage,pageSize);
        //将查询到的数据封装到PageResult中
        Page<User> userPage = userDao.selectPageByCondition(queryString);
        List<User> result = userPage.getResult();
        for (User user : result) {
            String gender = user.getGender();
            //性别格式转换
            if(gender != null){
                if(gender.equals("1")){
                    user.setGender("男");
                }
                if(gender.equals("2")){
                    user.setGender("女");
                }
            }
        }
        return new PageResult(userPage.getTotal(),result);
    }

    //xgw_用户管理新增用户
    @Override
    public void add(User user) {
       userDao.add(user);
    }

    //xgw_回显用户所对应的角色
    @Override
    public List<Integer> findRoleIdByUserId(Integer id) {
        return userDao.findRoleIdByUserId(id);
    }

    //xgw_增加用户角色
    @Override
    public void addRole(User user,Integer[] ids) {
        Integer userId = user.getId();
        //根据用户id查询角色是否存在
        Long count = userDao.findRoleIdCountByUserId(userId);
        if(count > 0){
            //存在相应角色,删除
            userDao.deleteRoleIdAll(userId);
        }
        //重新建立用户和角色中间表
        if(ids != null && ids.length > 0){
            for (Integer id : ids) {
                Map<String,Integer> map = new HashMap();
                map.put("userId",userId);
                map.put("roleId",id);
                userDao.addUserIdAndRoleId(map);
            }
        }
    }
}
