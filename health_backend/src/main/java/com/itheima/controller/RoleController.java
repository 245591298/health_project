package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    //xgw_查询所有角色
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<Role> roleList = roleService.findAll();
            return new Result(true, MessageConstant.QUERY_Role_SUCCESS,roleList);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_Role_FALL);
        }
    }
}
