package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    //添加预约
    void add(OrderSetting orderSetting);
    //修改预约
    void editNumberByOrderDate(OrderSetting orderSetting);
    //查询当前日期是否有数据
    long findCountByOrderDate(Date orderDate);
    //查询某一月份的预约情况
    List<OrderSetting> findAll(Map map);
    //查询某一天的是否有设置预约
    OrderSetting findByDate(Date date);
    //修改当天已预约人数
    void editReservationsByOrderDate(OrderSetting orderSetting);

}
