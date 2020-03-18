package com.itheima.pojo;

import java.io.Serializable;
import java.util.Date;

public class Body implements Serializable {
    private Integer id;
    private Date assessment_data;
    private Integer deficiency;
    private Integer yandeficiency;
    private Integer yindeficiency;
    private Integer member_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAssessment_data() {
        return assessment_data;
    }

    public void setAssessment_data(Date assessment_data) {
        this.assessment_data = assessment_data;
    }

    public Integer getDeficiency() {
        return deficiency;
    }

    public void setDeficiency(Integer deficiency) {
        this.deficiency = deficiency;
    }

    public Integer getYandeficiency() {
        return yandeficiency;
    }

    public void setYandeficiency(Integer yandeficiency) {
        this.yandeficiency = yandeficiency;
    }

    public Integer getYindeficiency() {
        return yindeficiency;
    }

    public void setYindeficiency(Integer yindeficiency) {
        this.yindeficiency = yindeficiency;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }
}
