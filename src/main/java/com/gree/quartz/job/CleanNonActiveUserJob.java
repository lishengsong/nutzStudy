package com.gree.quartz.job;

import com.gree.bean.User;
import com.gree.bean.UserProfile;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-13 15:45
 * @description:  添加任务类、清理用户需要清理2个表,新建类
 */
@IocBean
public class CleanNonActiveUserJob implements Job {

    private static final Log log = Logs.get();

    @Inject
    protected Dao dao;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.debug("clean Non-Active User , start");
        Date deadtime = new Date(System.currentTimeMillis() - 24*60*60*1000L); // 一天, 测试的时候可以改成1小时之类的
        Cnd cnd = Cnd.where("userId", ">", 3).and("createTime", "<", deadtime).and(Cnd.exps("emailChecked", "=", false).or("email", "IS", null));
        int deleted = dao.clear(UserProfile.class, cnd);
        log.debugf("delete %d UserProfile", deleted);

        Sql sql = Sqls.create("delete from $user_table where id > 10 and not exists (select 1 from $user_profile_table where $user_table.id = uid ) and ct < @deadtime");
        sql.vars().set("user_table", dao.getEntity(User.class).getTableName());
        sql.vars().set("user_profile_table", dao.getEntity(UserProfile.class).getTableName());
        sql.params().set("deadtime", deadtime);
        dao.execute(sql);
        log.debugf("delete %d User", sql.getUpdateCount());

        log.debug("clean Non-Active User , Done");
    }
}
