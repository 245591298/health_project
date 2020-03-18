package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zsOrder")
public class ZS_OrderController {
    @Reference
    private OrderService orderService;

  @RequestMapping(value = "/editOrderStatus")
    public  Result editOrderStatus(Integer id){
      try {
          orderService.zs_editOrderStatus(id);
          return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
      } catch (Exception e) {
          e.printStackTrace();
          return new Result(false,MessageConstant.ORDERSETTING_FAIL);
      }
  }
  @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
      Integer currentPage = queryPageBean.getCurrentPage();
      Integer pageSize = queryPageBean.getPageSize();
      String queryString = queryPageBean.getQueryString();
      PageResult pageResult=orderService.zs_findPage(currentPage,pageSize,queryString);
      System.out.println(pageResult);
      return pageResult;

  }
  //删除预约
  @RequestMapping(value = "/deleteOrder")
    public Result deleteOrder(Integer id){
      try {
          orderService.zs_deleteOrder(id);

          return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
      } catch (Exception e) {
          e.printStackTrace();
          return new Result(false,MessageConstant.ORDERSETTING_FAIL);
      }

  }

}
