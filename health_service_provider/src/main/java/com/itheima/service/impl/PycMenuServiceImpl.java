package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PycMenuDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;
import com.itheima.service.PycMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pyc
 */
@Service(interfaceClass = PycMenuService.class)
@Transactional
public class PycMenuServiceImpl implements PycMenuService {

    @Autowired
    private PycMenuDao menuDao;

    /**
     *  根据角色动态展示出对应的menu
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> PycfindMenuByRoleId(String username) {
        //构造一个list容器存放map用于返还数据
        List<Map<String,Object>> mapList=new ArrayList<>();
        //获取该role所有menu集合
        List<Menu> menuList = menuDao.findMenuByUsername(username);
        //所有的子目录
        List<Menu> allChildMenus=new ArrayList<>();
        //所有的根目录
        List<Menu> rootMenus=new ArrayList<>();
        //遍历menuList
        for (Menu menu : menuList) {
            if(0==menu.getParentMenuId()){
                //筛选出根目录
                rootMenus.add(menu);
            }else{
                //筛选出子目录
                allChildMenus.add(menu);
            }
        }
        //从根目录开始遍历
        for (Menu menu : rootMenus) {
            Map<String,Object> map=new HashMap<>();
            map.put("path",menu.getPath());
            map.put("icon",menu.getIcon());
            map.put("title",menu.getName());
            map.put("children",menu.getChildren());
            //查询出子集
            List<Menu> children = findChildren(menu, allChildMenus);
            //合并子集
            mergeChild(map,children,allChildMenus);
            //添加到集合
            mapList.add(map);
        }
        return mapList;
    }

    /**
     *  递归合并子集
     * @param fatherMap
     * @param children
     * @param allChildMenus
     */
    public void mergeChild(Map<String,Object> fatherMap,List<Menu> children,List<Menu> allChildMenus){
        if(children!=null&&children.size()>0){
            List<Map<String,Object>> mapList = (List<Map<String, Object>>) fatherMap.get("children");
            for (Menu child : children) {
                Map<String,Object> map=new HashMap<>();
                map.put("path",child.getPath());
                map.put("title",child.getName());
                map.put("linkUrl",child.getLinkUrl());
                map.put("children",child.getChildren());
                //查询出对应的子集
                List<Menu> children1 = findChildren(child, allChildMenus);
                mergeChild(map,children1,allChildMenus);
                mapList.add(map);
            }
        }
    }

    /**
     *  根据父菜单筛选出直接的子集
     * @param menu
     * @param childMenus
     * @return
     */
    public List<Menu> findChildren(Menu menu,List<Menu> childMenus){
        List<Menu> list=new ArrayList<>();
        Integer id = menu.getId();
        for (Menu childMenu : childMenus) {
            if (childMenu.getParentMenuId()==id) {
                list.add(childMenu);
            }
        }
        return list;
    }

    /**
     *  展示menu表格
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pycFindPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Menu> menus = menuDao.pycSelectByCondtion(queryPageBean.getQueryString());
        return new PageResult(menus.getTotal(),menus.getResult());
    }

    /**
     * 添加菜单
     * @param menu
     */
    @Override
    public void pycAddmenu(Menu menu) {
        menuDao.pycAddMenu(menu);
    }

    /**
     * 根据id查询菜单信息
     * @param id
     * @return
     */
    @Override
    public Menu pycFindMenuById(Integer id) {
        return menuDao.pycFindById(id);
    }

    @Override
    public void pycEditMenu(Menu menu) {
        menuDao.pycEditMenu(menu);
    }

}
