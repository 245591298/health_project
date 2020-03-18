package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderSettingServiceImpl
 * @Date 2020/2/26 12:18
 * @User 王骁
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> list) {
        if (list != null && list.size()>0){
            for (OrderSetting orderSetting : list) {
                //检验日期是否存在
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (count >0){
                    //存在则更新
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else {
                    //不存在则添加
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> findAll(String date) {
        //between begin and end
        String begin = date + "-1";
        String end = date + "-31";
        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);
        List<OrderSetting> list = orderSettingDao.findAll(map);
        //new一个map来存放对应的数据 new一个list存放多个map
        List<Map> data = new ArrayList<>();
        if (list != null && list.size()>0){
            for (OrderSetting orderSetting : list) {
                Map orderMap = new HashMap();
                orderMap.put("date",orderSetting.getOrderDate().getDate());//获得日期
                orderMap.put("number",orderSetting.getNumber());//可预约人数
                orderMap.put("reservations",orderSetting.getReservations());//已预约人数
                data.add(orderMap);
            }
        }
        return data;
    }

    //根据日期修改可预约人数
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        //当前日期已经进行了预约设置，需要进行修改操作
        if (count >0){
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
            //当前日期没有进行预约设置，进行添加操作
            orderSettingDao.add(orderSetting);
        }
    }
}
