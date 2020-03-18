package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.ChenChengAddress;
import com.itheima.service.ChenChengAddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
* 地图地址相关
*
* */
@RestController
@RequestMapping("/address")
public class ChenChengAddressController {
    @Reference
    private ChenChengAddressService chenChengAddressService;
    @RequestMapping("/findAll")//获取到所有的体检机构地址
    public Result findAll(){
        try {
            //查询成功,测试
            List<ChenChengAddress> list = chenChengAddressService.findAll();
            return new Result(true,"获取体检机构成功",list);
        } catch (Exception e) {
            //不打印错误信息,查询失败
            e.printStackTrace();
            return new Result(false,"获取体检机构地址失败");
        }
    }
    @RequestMapping("/findById")//根据用户选择的体检机构地址的id获取该地图经纬度
    public Result findById(Integer id){
        try {
            //查询成功
            String longitude = chenChengAddressService.findById(id);
            return new Result(true,"地图信息展示成功",longitude);
        } catch (Exception e) {
            e.printStackTrace();
            //不打印错误信息,查询失败
            return new Result(false,"地图信息展示失败");
        }
    }
}
