package com.itheima.dao;

import java.util.List;
import java.util.Map;

public interface ReportDao {
    //获取某日之前的会员数
    Integer getMembersBeforeDate(String date);
    //查询所有的套餐及其预约数量
    List<Map<String,Object>> getSetmealNameAndCount();
}
