package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import com.itheima.utils.QiniuUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName SetMealController
 * @Date 2020/2/23 18:38
 * @User 王骁
 */
@RestController //@ResponseBody + @Controller
@RequestMapping("/setmeal")
public class SetMealController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imFile){
        //获得上传图片的文件名
        String originalFilename = imFile.getOriginalFilename();
        //截取文件格式
        int i = originalFilename.lastIndexOf(".") - 1;
        String kuoZhanMing = originalFilename.substring(i); //文件扩展名  .jpg

        //随机生成(不重复)的图片文件名
        String fileName = UUID.randomUUID().toString()+kuoZhanMing;
        try {
            QiniuUtils.upload2Qiniu(imFile.getBytes(),fileName);
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }

    @Reference
    private SetMealService setMealService;

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal , Integer[] checkGroupIds){
        try {
            setMealService.add(setmeal,checkGroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setMealService.findPage(queryPageBean);
        return pageResult;
    }


}
