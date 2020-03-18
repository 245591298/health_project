package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Order;
import com.itheima.pojo.PycOrderManager;


import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderDao
 * @Date 2020/3/2 19:25
 * @User 王骁
 */
public interface OrderDao {
    //根据条件查询
    List<Order> findByCondition(Order order);
    //添加预约
    void add(Order order);
    //查询预约的详细信息
    Map findById4Detail(Integer id);

    Integer getTodayOrders(String today);

    Integer getTodayVisits(String today);

    Integer getOrdersAfterDate(String thisWeekMonday);

    Integer getVisitsAfterDate(String thisWeekMonday);

    List<Map> findHotSetmeal();

    Order wxFindByCondition(Order order);
    //分页查询ordermanager
    Page<PycOrderManager> selectByCondition(String queryString);

    void affirmOrder(Map<String, Object> map);
    
    void zs_editOrderStatus(Order order);

    Page<Order> zs_selectByCondition(String queryString);

    void zs_deleteOrderById(Integer id);

    void addAddress(Map addressMap);
}
