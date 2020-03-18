package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.ZS_Psychology;
import com.itheima.service.MemberService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/zs_psychology")
public class ZS_PsychologyListController {
    @Reference
    private MemberService memberService;
    @RequestMapping(value = "/zs_findPage")
    public PageResult zs_findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult=memberService.zs_findPage(queryPageBean);
        return pageResult;
    }
}
