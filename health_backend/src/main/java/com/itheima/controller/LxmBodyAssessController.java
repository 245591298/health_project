package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.LxmEditLevel;
import com.itheima.pojo.LxmRiskAssessment;
import com.itheima.service.LxmBodyAssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lxm
 */

@RestController
@RequestMapping("/bodyAssess")
public class LxmBodyAssessController {
    @Reference
    private LxmBodyAssessService lxmBodyAssessService;
    @Autowired
    private JedisPool jedisPool;
    //记录已痊愈用户id，在一次展示后清零
    private List<Integer> mList = new ArrayList<>();
    //记录发生改变的id,选用map是因为可以在二次修改时仍只保留唯一id
    private Map<Integer,String> cMap = new HashMap<>();

    //标识只执行一次findAll
    private Boolean flag = true;

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody Map map){
        try{
            PageResult pageResult = lxmBodyAssessService.findPage(map);
            return new Result(true,"成功获取风险评估数据",pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"获取风险评估数据失败");
        }
    }

    @RequestMapping("/editLevel")
    public void editLevel(@RequestBody LxmEditLevel lxmEditLevel) {
        //以会员id为key
        //先查询出还未修改前数据库中level等级，并存入redis中
        //避免修改一个，另一个已修改后的oldData也更新
        if(flag) {
            List<LxmRiskAssessment> list = lxmBodyAssessService.findAll();
            for (LxmRiskAssessment lxmRiskAssessment : list) {
                jedisPool.getResource().hset("a", lxmRiskAssessment.getId().toString(), lxmRiskAssessment.getLevel());
            }
            flag = false;
        }

        //修改数据库中病因数据内容,并返回封装了已计算好的level对象
        //查询只返回level不为null的数据
        LxmRiskAssessment lxmRiskAssessment = lxmBodyAssessService.editLevel(lxmEditLevel);
        //更新对应redis a 中的olderData值
        String n = jedisPool.getResource().hget("b", lxmRiskAssessment.getId() + "");
        if(n != null){
            jedisPool.getResource().hset("a",lxmRiskAssessment.getId()+"",n);
        }

        if (lxmRiskAssessment.getNum() == 0) {
            //将治愈用户id存入list集合
            mList.add(lxmEditLevel.getId());
            //删除之前存入redis的b中的值
            jedisPool.getResource().hdel("b",lxmEditLevel.getId()+"");
            //至于用户单独存储在可计时的redis中
            jedisPool.getResource().setex(lxmEditLevel.getId().toString(),30, "身体机能完全恢复");
        } else {
            //将发生改变且未康复的id存入cList
            cMap.put(lxmRiskAssessment.getId(),"a");
            //将修改后的level存入新的redis中
            jedisPool.getResource().hset("b", lxmEditLevel.getId().toString(), lxmRiskAssessment.getLevel());
            //判断先后level是否相同
            String a = jedisPool.getResource().hget("a", lxmEditLevel.getId().toString());
            String b = jedisPool.getResource().hget("b", lxmEditLevel.getId().toString());
            if (a.equals(b)) {
                jedisPool.getResource().hset("b", lxmEditLevel.getId().toString(), "病因可能发生转移");
            }
        }
    }

    @RequestMapping("/change")
    public List<Map> change(@RequestBody List<Map> preList){
        List<Integer> ids = new ArrayList<>();
        for (Map preMap : preList) {
            ids.add((int)preMap.get("id"));
        }
        List<Map> maps = new ArrayList<>();
        //对set redis的值获取
        if(mList.size() > 0) {
            for (Integer mid : mList) {
                Map<String, String> map = new HashMap<>();
                //获取已痊愈会员新mid值
                String newData = jedisPool.getResource().get(mid + "");
                //获取已痊愈会员旧mid值
                String oldData = jedisPool.getResource().hget("a", mid + "");

                map.put("id", mid + "");
                map.put("newData", newData);
                map.put("oldData", oldData);
                maps.add(map);
            }
        }
        //痊愈的id清零
        mList.clear();


        //对map redis的值获取
        for (int id : ids) {
            for (Integer mid : cMap.keySet()) {
                if(id == mid){
                    Map<String, String> map = new HashMap<>();
                    //获取病因发生变化的会员旧id值
                    String oldData = jedisPool.getResource().hget("a",id + "");
                    //获取已痊愈会员新id值
                    String newData = jedisPool.getResource().hget("b", id + "");

                    map.put("id",id+"");
                    map.put("newData",newData);
                    map.put("oldData",oldData);
                    maps.add(map);
                }
            }
        }

        return maps;
    }
}
