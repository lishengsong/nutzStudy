package com.gree.bean;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-10 16:50
 * @description: user bean
 */
@Table("t_user")
public class User {

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

    @Column("ct")
    @Prev(els = @EL("now()"))
    private Date createTime;

    @Column("ut")
    private Date updateTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
