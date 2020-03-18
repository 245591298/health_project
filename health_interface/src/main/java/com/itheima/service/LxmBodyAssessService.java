package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.LxmEditLevel;
import com.itheima.pojo.LxmRiskAssessment;

import java.util.List;
import java.util.Map;

/**
 * @author Lxm
 */
public interface LxmBodyAssessService {
    PageResult findPage(Map map);

    List<LxmRiskAssessment> findAll();

    LxmRiskAssessment editLevel(LxmEditLevel lxmEditLevel);
}
