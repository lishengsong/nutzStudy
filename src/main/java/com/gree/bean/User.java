package com.gree.bean;

import org.nutz.dao.entity.annotation.*;

import java.util.Date;
import java.util.List;

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

    @Column
    private boolean locked;
    @ManyMany(from="u_id", relation="t_user_role", target=Role.class, to="role_id")
    protected List<Role> roles;
    @ManyMany(from="u_id", relation="t_user_permission", target=Permission.class, to="permission_id")
    protected List<Permission> permissions;

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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }
}
