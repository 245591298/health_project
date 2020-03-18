package com.itheima.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pyc
 */
public class PycPsychology implements Serializable {

    private Integer id;
    private Integer body;
    private Date assessment_date;
    private Integer obsession;
    private Integer anxious;
    private Integer paranoia;
    private Integer member_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBody() {
        return body;
    }

    public void setBody(Integer body) {
        this.body = body;
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

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }
}
