package com.gree.module;

import com.gree.bean.User;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @Author :Web寻梦狮（lishengsong）
 * @Date Created in 下午9:45 2018/1/10
 * @Description: UserModule
 */
@Filters(@By(type = CheckSession.class, args = {"me", "/user/login"})) //含义是,如果当前Session没有带me这个attr,就跳转到/页面,即首页.
@At("/user")
@Ok("json:{locked:'password|salt'}") //另外, 密码和salt也不可以发送到浏览器去
@Fail("http:500")
@IocBean // 还记得@IocBy吗? 这个跟@IocBy有很大的关系哦
public class UserModule {

    private static final Log log = Logs.get();

    @Inject
    protected Dao dao; // 就这么注入了,有@IocBean它才会生效

    @At
    public int count() {

        return dao.count(User.class);
    }

    //同时,为login方法设置为空的过滤器,不然就没法登陆了

    @Filters
    @GET
    @At({"/login"})
    @Ok("jsp:jsp.user.login")
    public void loginPage() {}

    @GET
    @At({"/", "/index"})
    @Ok("jsp:jsp.user.index")
    public void indexPage() {}
    @GET
    @At({"/list"})
    @Ok("jsp:jsp.user.ajaxList")
    public void ajaxListPage() {}

    @Filters
    @POST
    @At
    public NutMap login(@Param("username")String username,@Param("password")String password, HttpSession session) {
        NutMap re = new NutMap("ok", false);
        if (Strings.isBlank(username) || Strings.isBlank(password)) {
            log.debug("username or password is null");
            return re.setv("language", "用户名或密码不能为空");
        }
        User user = dao.fetch(User.class, username);
        if (user == null) {
            log.debug("no such user = " + username);
            return re.setv("language", "没有该用户");
        }
        //String tmp = Lang.digest("SHA-256", user.getSalt() + password);
        if (!password.equals(user.getPassword())) {
            log.debug("password is wrong");
            return re.setv("language", "密码错误");
        }
        session.setAttribute("me", user);
        return re.setv("ok", true);
    }

    @At
    @Ok(">>:/")//>> 和 -> 分别是 redirect 和 forward的缩写
    public void logout(HttpSession session) {
        session.invalidate();
    }


    @At
    @Ok("jsp:jsp.user.list") //内部跳转到jsp,直接访问不了
    public QueryResult query(@Param("username")String name,@Param("..") Pager pager) {

        Cnd cnd = Strings.isBlank(name) ? null : Cnd.where("name", "like", "%"+name+"%");
        List<User> users = dao.query(User.class, cnd , pager);
        pager.setRecordCount(dao.count(User.class));
        QueryResult qr = new QueryResult(users, pager);//默认分页是第1页,每页20条
        return qr;
    }

    @POST
    @At
    public QueryResult list(@Param("username")String name,@Param("..") Pager pager) {
        Cnd cnd = Strings.isBlank(name) ? null : Cnd.where("name", "like", "%"+name+"%");
        List<User> users = dao.query(User.class, cnd , pager);
        pager.setRecordCount(dao.count(User.class));
        QueryResult qr = new QueryResult(users, pager);
        return qr;
    }


    @POST
    @At
    public NutMap add(@Param("..")User user){

        NutMap result = new NutMap("ok",false);

        if(Strings.isBlank(user.getName())) return result.setv("language","用户名不能为空！");
        if(Strings.isBlank(user.getPassword())) return result.setv("language","密码不能为空");
        if(dao.fetch(User.class,user.getName()) != null)
        return  result.setv("language","该用户名已经存在aaf");

        dao.insert(user);
        if(user.getId()>0){
            return result.setv("ok",true).setv("language","添加成功");
        }else return result.setv("language","sql出现异常");

    }
    @POST
    @At
    public Object update(@Param("..")User user) {
        NutMap re = new NutMap();
        String msg = checkUser(user, false);
        if (msg != null){
            return re.setv("ok", false).setv("language", msg);
        }
        user.setName(null);// 不允许更新用户名
        user.setCreateTime(null);//也不允许更新创建时间
        user.setUpdateTime(new Date());// 设置正确的更新时间
        dao.updateIgnoreNull(user);// 真正更新的其实只有password和salt
        return re.setv("ok", true);
    }


    @POST
    @At
    public Object delete(@Param("id")int id, @Attr("me")User me) {
        if (me.getId() == id) {
            return new NutMap().setv("ok", false).setv("language", "不能删除当前用户!!");
        }
        dao.delete(User.class, id); // 再严谨一些的话,需要判断是否为>0
        return new NutMap().setv("ok", true);
    }


    protected String checkUser(User user, boolean create) {
        if (user == null) {
            return "空对象";
        }
        if (create) {
            if (Strings.isBlank(user.getName()) || Strings.isBlank(user.getPassword()))
                return "用户名/密码不能为空";
        } else {
            if (Strings.isBlank(user.getPassword()))
                return "密码不能为空";
        }
        String passwd = user.getPassword().trim();
        if (6 > passwd.length() || passwd.length() > 12) {
            return "密码长度错误";
        }
        user.setPassword(passwd);
        if (create) {
            int count = dao.count(User.class, Cnd.where("name", "=", user.getName()));
            if (count != 0) {
                return "用户名已经存在";
            }
        } else {
            if (user.getId() < 1) {
                return "用户Id非法";
            }
        }
        if (user.getName() != null)
            user.setName(user.getName().trim());
        return null;
    }


}
