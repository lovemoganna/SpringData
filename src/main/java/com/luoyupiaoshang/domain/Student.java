package com.luoyupiaoshang.domain;

/**
 * @author: ligang
 * date: 2018/1/24
 * time: 10:49
 * 定义实体类
 */
public class Student {
    /**
     * 主键字段
     */
    private int id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;

    //下面是get,Set方法

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
