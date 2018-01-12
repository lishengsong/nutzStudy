package com.gree.bean;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-12 11:17
 * @description: 这个类是User类的详细信息类
 */
import java.sql.Blob;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.json.JsonField;

@Table("t_user_profile")
public class UserProfile extends BaseBean {

    /**关联的用户id*/
    @Id(auto=false)
    @Column("uid")
    protected int userId;
    /**用户昵称*/
    @Column
    protected String nickname;
    /**用户邮箱*/
    @Column
    protected String email;
    /**邮箱是否已经验证过*/
    @Column("email_checked")
    protected boolean emailChecked;
    /**头像的byte数据*/
    @Column
    @JsonField(ignore=true)
    protected byte[] avatar;
    /**性别*/
    @Column
    protected String gender;
    /**自我介绍*/
    @Column("dt")
    protected String description;
    @Column("loc")
    protected String location;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailChecked() {
        return emailChecked;
    }

    public void setEmailChecked(boolean emailChecked) {
        this.emailChecked = emailChecked;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
