package com.itheima.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lxm
 */
public class LxmRiskAssessment implements Serializable {
    private Integer id;       //会员Id
    private String fileNumber;   //档案号
    private String name;       //会员名
    private String assessment_data;  //评估时间
    private String operator;    //操作人
    private String reportStatus;    //报告状态
    private String level;   //严重程度
    private Integer num;   //可能诱发患病数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

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

    public String getAssessment_data() {
        return assessment_data;
    }

    public void setAssessment_data(String assessment_data) {
        this.assessment_data = assessment_data;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
