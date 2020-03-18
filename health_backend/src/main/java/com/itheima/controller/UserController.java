package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName UserController
 * @Date 2020/3/4 11:30
 * @User 王骁
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/getUsername")
    public Result getUsername(){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_USERNAME_FAIL);
        }

    }
    //xgw_用户管理分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = userService.findPage(queryPageBean);
        return pageResult;
    }

    //xgw_用户管理新增用户
    @RequestMapping("/add")
    public Result add(@RequestBody com.itheima.pojo.User user){
        try {
            userService.add(user);
        } catch (Exception e) {
            return  new Result(false,MessageConstant.ADD_UserMember_FALL);
        }
        return new Result(true,MessageConstant.ADD_UserMember_SUCCESS);
    }

    //xgw_回显用户所对应的角色
    @RequestMapping("/findRoleIdByUserId")
    public Result findRoleIdByUserId(Integer id){
        try {
            List<Integer> ids =  userService.findRoleIdByUserId(id);
            return new Result(true,MessageConstant.QUERY_Role_SUCCESS,ids);
        } catch (Exception e) {
            return  new Result(false,MessageConstant.QUERY_Role_FALL);
        }
    }

    //xgw_增加用户角色
    @RequestMapping("/addRole")
    public Result addRole(@RequestBody com.itheima.pojo.User user, Integer[] roleIds){
        try {
           userService.addRole(user,roleIds);
            return new Result(true,MessageConstant.ADD_Role_SUCCESS);
        } catch (Exception e) {
            return  new Result(false,MessageConstant.ADD_Role_FALL);
        }
    }
}
