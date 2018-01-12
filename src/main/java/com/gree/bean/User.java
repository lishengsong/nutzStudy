package com.gree.bean;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-10 16:50
 * @description: user bean
 */
@Table("t_user")
public class User extends  BaseBean {

    @Id
    private int id;

    @Name
    private String name;

    @Column("passwd")
    @ColDefine(width = 128)
    private String password;

    @Column("salt")
    @ColDefine(width = 128)
    private String salt;

    @Column
    private int age;

    @One(field="id", key="userId") // 1.r.59之前需要写target参数
    protected UserProfile profile;



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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }




}
