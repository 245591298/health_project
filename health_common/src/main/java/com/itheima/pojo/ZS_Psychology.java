package com.itheima.pojo;

import java.io.Serializable;
import java.util.Date;

public class ZS_Psychology implements Serializable {
    private String fileNumber;//档案号
    private String name;//姓名
    private Date assessment_date;
    private Integer obsession;
    private Integer anxious;
    private Integer paranoia;

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAssessment_date() {
        return assessment_date;
    }

    public void setAssessment_date(Date assessment_date) {
        this.assessment_date = assessment_date;
    }

    public Integer getObsession() {
        return obsession;
    }

    public void setObsession(Integer obsession) {
        this.obsession = obsession;
    }

    public Integer getAnxious() {
        return anxious;
    }

    public void setAnxious(Integer anxious) {
        this.anxious = anxious;
    }

    public Integer getParanoia() {
        return paranoia;
    }

    public void setParanoia(Integer paranoia) {
        this.paranoia = paranoia;
    }
}
