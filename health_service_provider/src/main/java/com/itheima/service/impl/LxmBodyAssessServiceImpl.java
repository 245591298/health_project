package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.LxmBodyAssessDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.LxmEditLevel;
import com.itheima.pojo.LxmRiskAssessment;
import com.itheima.service.LxmBodyAssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lxm
 */

@Service(interfaceClass = LxmBodyAssessService.class)
@Transactional
public class LxmBodyAssessServiceImpl implements LxmBodyAssessService {
    @Autowired
    private LxmBodyAssessDao lxmBodyAssessDao;

    @Override
    public PageResult findPage(Map map) {
        //开启分页查询
        PageHelper.startPage((int)map.get("currentPage"),(int)map.get("pageSize"));
        Page<LxmRiskAssessment> page = lxmBodyAssessDao.findPage((String)map.get("queryString"));
        List<LxmRiskAssessment> lists = page.getResult();

        //把分页获取的不完整数据，进行二次填充
        List<LxmRiskAssessment> list = new ArrayList<>();
        for (LxmRiskAssessment pageL : lists) {
            LxmRiskAssessment lxmRiskAssessment = new LxmRiskAssessment();
            lxmRiskAssessment.setId(pageL.getId());
            lxmRiskAssessment.setAssessment_data(pageL.getAssessment_data());
            lxmRiskAssessment.setFileNumber(pageL.getFileNumber());
            lxmRiskAssessment.setName(pageL.getName());
            //填充专有数据
            lxmRiskAssessment.setOperator("lxm");
            lxmRiskAssessment.setReportStatus("未出报告");
            //根据可能诱发疾病的因子数个数，进行分类
            if(pageL.getNum() > 0){
                if(pageL.getNum() == 1){
                    lxmRiskAssessment.setLevel("普通");
                }else if(pageL.getNum() == 2){
                    lxmRiskAssessment.setLevel("亚健康");
                }else if(pageL.getNum() == 3){
                    lxmRiskAssessment.setLevel("严重");
                }else {
                    lxmRiskAssessment.setLevel("非常严重");
                }
                list.add(lxmRiskAssessment);
            }
        }
        long total = page.getTotal();
        return new PageResult(total,list);
    }

    @Override
    public List<LxmRiskAssessment> findAll() {
        List<LxmRiskAssessment> lists = lxmBodyAssessDao.findAll();
        List<LxmRiskAssessment> list = new ArrayList<>();
        for (LxmRiskAssessment pageL : lists) {
            LxmRiskAssessment lxmRiskAssessment = new LxmRiskAssessment();
            lxmRiskAssessment.setId(pageL.getId());
            lxmRiskAssessment.setAssessment_data(pageL.getAssessment_data());
            lxmRiskAssessment.setFileNumber(pageL.getFileNumber());
            lxmRiskAssessment.setName(pageL.getName());
            //根据可能诱发疾病的因子数个数，进行分类
            if(pageL.getNum() > 0){
                if(pageL.getNum() == 1){
                    lxmRiskAssessment.setLevel("普通");
                }else if(pageL.getNum() == 2){
                    lxmRiskAssessment.setLevel("亚健康");
                }else if(pageL.getNum() == 3){
                    lxmRiskAssessment.setLevel("严重");
                }else {
                    lxmRiskAssessment.setLevel("非常严重");
                }
                list.add(lxmRiskAssessment);
            }
        }
        return list;
    }

    @Override
    public LxmRiskAssessment editLevel(LxmEditLevel lxmEditLevel) {
        //修改病因
        lxmBodyAssessDao.editLevel(lxmEditLevel);
        //获取修改后的数据
        LxmRiskAssessment lxmRiskAssessment = lxmBodyAssessDao.findLevel(lxmEditLevel.getId());
        if(lxmRiskAssessment.getNum() > 0){
            if(lxmRiskAssessment.getNum() == 1){
                lxmRiskAssessment.setLevel("普通");
            }else if(lxmRiskAssessment.getNum() == 2){
                lxmRiskAssessment.setLevel("亚健康");
            }else if(lxmRiskAssessment.getNum() == 3){
                lxmRiskAssessment.setLevel("严重");
            }else {
                lxmRiskAssessment.setLevel("非常严重");
            }
        }
        return lxmRiskAssessment;
    }
}
