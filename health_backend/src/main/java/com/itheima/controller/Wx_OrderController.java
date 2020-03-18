package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Wx_OrderController
 * @Date 2020/3/10 11:31
 * @User 王骁
 * 新增预约
 */
@RestController
@RequestMapping("/wx_order")
public class Wx_OrderController {
    @Reference
    private OrderService orderService;

    @RequestMapping("/add")
    public Result add(@RequestBody Map<String,Object> basicData,Integer[] setmealIds){
        String orderDate = (String) basicData.get("orderDate");
        Date date = null;
        try {
            date = DateUtils.parseString2Date(orderDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        date=calendar.getTime();
        try {
            String dateInMap = DateUtils.parseDate2String(date);
            basicData.put("orderDate",dateInMap);
            Result result = orderService.wxAddOrder(basicData, setmealIds);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/affirmOrder")
    public Result affirmOrder(Integer id){
        try{
            orderService.affirmOrder(id);
            return new Result(true,"已确认预约");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"确认预约失败");
        }
    }

}
