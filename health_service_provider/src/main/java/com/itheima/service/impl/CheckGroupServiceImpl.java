package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckGroupServiceImpl
 * @Date 2020/2/21 22:42
 * @User 王骁
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    //新增检查组 同时让检查组关联检查项
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        this.setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    //分页查询检查组
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //通过id查找检查组
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    //修改检查组
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.修改检查组的基本数据
        checkGroupDao.editCheckGroup(checkGroup);
        //2.将检查组关联的所有检查项都删除
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //3.重新添加检查项
        this.setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    //查询检查组对应的检查项
    @Override
    public List<Integer> findCheckitemsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckitemsByCheckGroupId(id);
    }

    //删除检查组
    @Override
    public void delete(Integer id) {
        //1.先在中间表删除检查组对与其对应的检查项
        checkGroupDao.deleteCheckGroupIdAndCheckItemIds(id);
        //2.删除检查组
        checkGroupDao.deleteCheckGroup(id);
    }

    //查询所有检查组
    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> list = checkGroupDao.findAll();
        return list;
    }

    //设置检查组和检查项的关系
    public void setCheckGroupAndCheckItem(Integer checkGroupId ,Integer[] checkitemIds){
        if (checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

}
