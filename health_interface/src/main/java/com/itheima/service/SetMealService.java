package com.itheima.service;

/**
 * @ClassName SetMealService
 * @Date 2020/2/23 19:48
 * @User 王骁
 */

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;

/**
 * 套餐服务接口
 */
public interface SetMealService {

    //添加套餐
    void add(Setmeal setmeal , Integer[] checkGroupIds);
    //查询套餐数据(分页)
    PageResult findPage(QueryPageBean queryPageBean);
    //获得所有的套餐
    List<Setmeal> getAllSetmeal();
    //根据id查询套餐项
    Setmeal findById(int id);
}
