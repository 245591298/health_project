package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SetMealDao
 * @Date 2020/2/23 19:47
 * @User 王骁
 */
public interface SetMealDao {
    //添加套餐
    void addSetMeal(Setmeal setmeal);

    //添加套餐相关的检查组
    void addSetMealAndCheckGroup(Map<String,Integer> map);

    //根据条件查询套餐
    Page<Setmeal> findByCondition(String queryString);

    //查询所有的套餐
    List<Setmeal> getAllSetmeal();

    //根据id查询套餐
    Setmeal findById(int id);


    String findNameById(Integer id);

    void zs_deleteSetMealById(Integer id);
}
