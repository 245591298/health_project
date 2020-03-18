package com.itheima.pojo;

import java.io.Serializable;
/*
* 陈城
* 数据库t_address表对应的实体类
* */
public class ChenChengAddress implements Serializable {
    private Integer id;//主键
    private String name;//name
    private String address;//地址
    private String image;//图片
    private String longitude;//经纬度
    private String telephone;//电话号码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    //有参构造
    public ChenChengAddress(Integer id, String name, String address, String image, String longitude, String telephone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
        this.longitude = longitude;
        this.telephone = telephone;
    }
    //无参构造
    public ChenChengAddress() {
    }
    //方便查看数据
    @Override
    public String toString() {
        return "ChenChengAddress{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", longitude='" + longitude + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
