package com.itheima.pojo;

import java.io.Serializable;

//Xgw_会员疾病pojo
public class XgwMemberDifference implements Serializable {
    private String fileNumber;
    private String mName;
    private String sex;
    private Integer age;
    private String station;
    private String iName;
    private String manageMan;

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public String getManageMan() {
        return manageMan;
    }

    public void setManageMan(String manageMan) {
        this.manageMan = manageMan;
    }
}
