package com.gree.bean;

import org.nutz.dao.entity.annotation.*;

/**
 * @Author :Web寻梦狮（lishengsong）
 * @Date Created in 下午1:12 2018/1/14
 * @Description: Permission
 */
@Table("t_permission")
public class Permission extends BaseBean{


    @Id
    protected long id;
    @Name
    protected String name;
    @Column("al")
    protected String alias;
    @Column("dt")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
