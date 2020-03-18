package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 预约服务接口
 */
public interface OrderSettingService {
    //添加预约
    void add(List<OrderSetting> list);

    //查询预约
    List<Map> findAll(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
