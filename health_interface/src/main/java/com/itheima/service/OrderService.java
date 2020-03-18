package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;

import java.util.Map;

/**
 * 体检预约服务接口
 */
public interface OrderService {
    //体检预约
    Result order(Map map,String addressId) throws Exception;
    //查询预约的详情
    Map findById(Integer id) throws Exception;

    //后台系统添加预约
    public Result wxAddOrder(Map<String,Object> map,Integer[] setmealIds) throws Exception;


    //分页查询order信息
    PageResult pycFindPage(QueryPageBean queryPageBean);

    void affirmOrder(Integer id);

    //通过id改变order表状态
    void zs_editOrderStatus(Integer id);
    //分页操作
    PageResult zs_findPage(Integer currentPage, Integer pageSize, String queryString);
    //取消预约
    void zs_deleteOrder(Integer id);
}
