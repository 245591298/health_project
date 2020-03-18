package com.itheima.pojo;

import java.io.Serializable;

public class LxmEditLevel implements Serializable {
    private Integer id;
    private Integer deficiency;
    private Integer yandeficiency;
    private Integer yindeficiency;
    private Integer Obsession;
    private Integer anxious;
    private Integer Paranoia;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getObsession() {
        return Obsession;
    }

    public void setObsession(Integer obsession) {
        Obsession = obsession;
    }

    public Integer getAnxious() {
        return anxious;
    }

    public void setAnxious(Integer anxious) {
        this.anxious = anxious;
    }

    public Integer getParanoia() {
        return Paranoia;
    }

    public void setParanoia(Integer paranoia) {
        Paranoia = paranoia;
    }
}
