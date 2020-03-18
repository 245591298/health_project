package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author pyc
 */
@RestController
@RequestMapping("/pyc_order")
public class PycOrderController {

    @Reference
    private OrderService orderService;


    /**
     *  蒲云聪 分页查询order信息
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/pyc_findPage")
    public PageResult pycFindPage(@RequestBody QueryPageBean queryPageBean){

           return  orderService.pycFindPage(queryPageBean);
    }

}
