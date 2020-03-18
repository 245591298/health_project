package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.PycMenuService;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author pyc
 */
@RestController
@RequestMapping("/pyc_menu")
public class PycMenuController {

    @Reference
    private PycMenuService menuService;

    @RequestMapping("pyc_findMenuByRole")
    public List<Map<String, Object>> pycFindMenuByRole() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return menuService.PycfindMenuByRoleId(user.getUsername());
    }


    @RequestMapping("pyc_findPage")
    public PageResult pycFindPage(@RequestBody QueryPageBean queryPageBean) {
        return menuService.pycFindPage(queryPageBean);
    }

    @RequestMapping("/pyc_addMenu")
    public Result pycAddMenu(@RequestBody Menu menu) {
        try {
            menuService.pycAddmenu(menu);
            return new Result(true, "添加菜单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "获取菜单信息失败");
        }
    }

    @RequestMapping("/pyc_findMenuById")
    public Result pycFindMenuById(Integer id) {
        try {
            Menu menu = menuService.pycFindMenuById(id);
            return new Result(true, "获取菜单信息成功", menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "获取菜单信息失败");
        }
    }

    @RequestMapping("/pyc_editMenu")
    public Result pycEditMenu(@RequestBody Menu menu){
        try {
            menuService.pycEditMenu(menu);
            return new Result(true,"编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑失败");
        }

    }
}
