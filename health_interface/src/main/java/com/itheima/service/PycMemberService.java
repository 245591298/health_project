package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.PycIllness;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author pyc
 */
public interface PycMemberService {

    PageResult pycFindPage(QueryPageBean queryPageBean);

    List<PycIllness> pycFindAllillness();

    void pycAddMember(Map map, Integer[] illnessIds) throws InvocationTargetException, IllegalAccessException;

    Map<String, Object> pycFindMemberInfoById(Integer id);

    void pycEditMemberInfo(Map map, Integer[] illnessIds) throws InvocationTargetException, IllegalAccessException;
}
