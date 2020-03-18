package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName SetMealController
 * @Date 2020/2/27 11:13
 * @User 王骁
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Reference
    private SetMealService setMealService;

    @RequestMapping("/getAllSetmeal")
    public Result getAllSetmeal(){
        try {
            List<Setmeal> list = setMealService.getAllSetmeal();
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping("/findById")
    private Result findById(Integer id){
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}
