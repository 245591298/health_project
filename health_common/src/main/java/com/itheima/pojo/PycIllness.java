package com.itheima.pojo;

import java.io.Serializable;

/**
 * @author pyc
 */
public class PycIllness implements Serializable {

    private Integer id;

    private String name;

    private Integer level;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
