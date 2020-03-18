package com.itheima.service;

import java.util.List;
import java.util.Map;

public interface ReportService {
    //根据月份查询会员数量的详情
    List<Integer> getMembersByMonths(List<String> list);
    //查询所有的套餐及其预约数量
    List<Map<String,Object>> getSetmealNameAndCount();
    //获取运营数据
    Map<String,Object> getBusinessReportData() throws Exception;

}
