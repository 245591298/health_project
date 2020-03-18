package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.ChenChengAddress;
import com.itheima.service.AddressService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

@RestController
@RequestMapping("/jianjiahaoAddressController")
public class JjhAddressController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private AddressService addressService;

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult page = addressService.findPage(queryPageBean);
            System.out.println(page.getRows().size());
            return new Result(true, "查询成功", page);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询地址数据失败");
        }

    }

    @RequestMapping("/update")
    public Result update(@RequestBody ChenChengAddress chenChengAddress) {
        try {
            addressService.editAddress(chenChengAddress);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }

    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            addressService.delAddress(id);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }

    }

    @RequestMapping("/add")
    public Result add(@RequestBody ChenChengAddress chenChengAddress) {

        try {
            addressService.addAddress(chenChengAddress);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }

    //图片上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imFile) {
        //获得上传图片的文件名
        String originalFilename = imFile.getOriginalFilename();
        //截取文件格式
        int i = originalFilename.lastIndexOf(".") - 1;
        String kuoZhanMing = originalFilename.substring(i); //文件扩展名  .jpg

        //随机生成(不重复 )的图片文件名
        String fileName = UUID.randomUUID().toString() + kuoZhanMing;
        try {
            QiniuUtils.upload2Qiniu(imFile.getBytes(), fileName);
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
        } catch (Exception e) {
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
    }
    //根据id回显体检机构的信息
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            ChenChengAddress chenChengAddress = addressService.findById(id);
            return new Result(true, "成功",chenChengAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "数据查询失败");
        }
    }
}
