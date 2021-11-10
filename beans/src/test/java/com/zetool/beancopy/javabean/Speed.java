package com.zetool.beancopy.javabean;

import com.zetool.beancopy.annotation.CopyFrom;

import java.util.Date;

@CopyFrom(sourceClass = Speed.class)
public class Speed {
    private Integer id = 1;

    private String name = "123";

    private Integer age = 3;

    private Date birthday = new Date();

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
