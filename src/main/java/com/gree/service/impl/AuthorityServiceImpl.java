package com.gree.service.impl;

import com.gree.bean.Permission;
import com.gree.bean.Role;
import com.gree.bean.User;
import com.gree.service.AuthorityService;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.Date;
import java.util.List;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-19 14:10
 * @description: 权限实现类
 */
@IocBean
public class AuthorityServiceImpl implements AuthorityService {
    @Inject
    protected Dao dao ;

    public void initFormPackage(String pkg) {

    }

    public void checkBasicRoles(User admin) {
        // 检查一下admin的权限
        Role adminRole = dao.fetch(Role.class, "admin");
        if (adminRole == null) {
            adminRole = addRole("admin");
        }
        // admin账号必须存在与admin组
        if (0 == dao.count("t_user_role", Cnd.where("u_id", "=", admin.getId()).and("role_id", "=", adminRole.getId()))) {
            dao.insert("t_user_role", Chain.make("u_id", admin.getId()).add("role_id", adminRole.getId()));
        }
        // admin组必须有authority:* 也就是权限管理相关的权限
        List<Record> res = dao.query("t_role_permission", Cnd.where("role_id", "=", adminRole.getId()));
        OUT: for (Permission permission : dao.query(Permission.class, Cnd.where("name", "like", "authority:%").or("name", "like", "user:%"), null)) {
            for (Record re : res) {
                if (re.getInt("permission_id") == permission.getId())
                    continue OUT;
            }
            dao.insert("t_role_permission", Chain.make("role_id", adminRole.getId()).add("permission_id", permission.getId()));
        }
    }

    public void addPermission(String permission) {

    }

    public Role addRole(String rolename) {

        Role role = new Role();
        role.setName(rolename);
        role.setAlias("超级管理员");
        Date date = new Date();
        role.setCreateTime(date);
        role.setUpdateTime(date);
        dao.insert(role);
        return role;
    }
}
