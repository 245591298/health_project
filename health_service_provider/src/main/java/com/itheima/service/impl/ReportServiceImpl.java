package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.ReportDao;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName ReportServiceImpl
 * @Date 2020/3/4 14:37
 * @User 王骁
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Integer> getMembersByMonths(List<String> months) {
        List<Integer> membersCount = new ArrayList<>();
        for (String month : months) {
            String date =month+".31";
            Integer members = reportDao.getMembersBeforeDate(date);
            membersCount.add(members);
        }
        return membersCount;
    }

    @Override
    public List<Map<String, Object>> getSetmealNameAndCount() {
        return reportDao.getSetmealNameAndCount();
    }

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        Map<String,Object> data = new HashMap<>();
        //获取当前时间 reportDate
        String today = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        //获取今日新增会员
        Integer todayNewMember = memberDao.getMembersByDate();
        //获取全部会员数
        Integer totalMember = memberDao.getTotalMembers();
        //获取本周新增会员数
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        Integer thisWeekNewMember = memberDao.getMembersAfterDate(thisWeekMonday);
        //获取本月新增会员数量
        String firstDayInMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        Integer thisMonthNewMember = memberDao.getMembersAfterDate(firstDayInMonth);
        //获取今天预约体检数量
        Integer todayOrderNumber = orderDao.getTodayOrders(today);
        //获取今天的到访的体检数
        Integer todayVisitsNumber = orderDao.getTodayVisits(today);
        //获取本周的预约数量
        Integer thisWeekOrderNumber = orderDao.getOrdersAfterDate(thisWeekMonday);
        //获取本周的到访的体检数
        Integer thisWeekVisitsNumber = orderDao.getVisitsAfterDate(thisWeekMonday);
        //获取本月的预约数量
        Integer thisMonthOrderNumber = orderDao.getOrdersAfterDate(firstDayInMonth);
        //获取本月的到诊数量
        Integer thisMonthVisitsNumber = orderDao.getVisitsAfterDate(firstDayInMonth);
        //热门套餐（取前4）
        List<Map> hotSetmeal = orderDao.findHotSetmeal();

        data.put("reportDate",today);
        data.put("todayNewMember",todayNewMember);
        data.put("totalMember",totalMember);
        data.put("thisWeekNewMember",thisWeekNewMember);
        data.put("thisMonthNewMember",thisMonthNewMember);
        data.put("todayOrderNumber",todayOrderNumber);
        data.put("thisWeekOrderNumber",thisWeekOrderNumber);
        data.put("thisMonthOrderNumber",thisMonthOrderNumber);
        data.put("todayVisitsNumber",todayVisitsNumber);
        data.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        data.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        data.put("hotSetmeal",hotSetmeal);


        return data;
    }

    public static void main(String[] args) throws Exception {
        String firstDayInMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        System.out.println(firstDayInMonth);
    }
}
