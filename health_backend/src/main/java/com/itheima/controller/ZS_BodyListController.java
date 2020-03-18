package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.service.MemberService;
import com.itheima.service.ZS_BodyListService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/zs_body")
public class ZS_BodyListController {
    @Reference
    private ZS_BodyListService zs_bodyListService;
    @RequestMapping(value = "/zs_findPage")
    public PageResult zs_findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult=zs_bodyListService.zs_findPage(queryPageBean);
        return pageResult;
    }

}
