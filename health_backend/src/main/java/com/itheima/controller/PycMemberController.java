package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.PycIllness;
import com.itheima.service.MemberService;
import com.itheima.service.PycMemberService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author pyc
 */

@RestController
@RequestMapping("/pyc_member")
public class PycMemberController {

    @Reference
    private PycMemberService memberService;

    @RequestMapping("/pyc_findPage")
    public PageResult pycFindPage(@RequestBody QueryPageBean queryPageBean) {
        return memberService.pycFindPage(queryPageBean);
    }

    @RequestMapping("/pyc_addMember")
    public Result pycAddMember(@RequestBody Map map, Integer[] illnessIds) {
        try {
            memberService.pycAddMember(map, illnessIds);
            return new Result(true, MessageConstant.ADD_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_MEMBER_FAIL);
        }
    }

    /**
     * 查询所有的illness用于显示
     *
     * @return
     */
    @RequestMapping("/pyc_findAllillness")
    public List<PycIllness> pycFindAllillness() {
        return memberService.pycFindAllillness();
    }

    /**
     *  根据memberId查询出memberinfo
     * @param id
     * @return
     */
    @RequestMapping("/pycFindMemberInfoById")
    public Result pycFindMemberInfoById(Integer id) {
        try {
            Map<String, Object> map  = memberService.pycFindMemberInfoById(id);
            return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    /**
     *  编辑会员信息
     * @param map
     * @param illnessIds
     * @return
     */
    @RequestMapping("/pycEditMemberInfo")
    public Result pycEditMemberInfo(@RequestBody Map map,Integer[] illnessIds){
        try {
            memberService.pycEditMemberInfo(map,illnessIds);
            return new Result(true,MessageConstant.EDIT_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_MEMBER_FAIL);
        }
    }
}
