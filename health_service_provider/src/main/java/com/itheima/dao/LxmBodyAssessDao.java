package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.LxmEditLevel;
import com.itheima.pojo.LxmRiskAssessment;

import java.util.List;

/**
 * @author Lxm
 */
public interface LxmBodyAssessDao {
    Page<LxmRiskAssessment> findPage(String queryString);

    List<LxmRiskAssessment> findAll();

    void editLevel(LxmEditLevel lxmEditLevel);

    LxmRiskAssessment findLevel(Integer id);
}
