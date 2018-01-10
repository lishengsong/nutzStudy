package com.gree.module;

import com.gree.bean.User;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Author :Web寻梦狮（lishengsong）
 * @Date Created in 下午9:45 2018/1/10
 * @Description: UserModule
 */

@At("/user")
@Ok("json")
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

    @GET
    @At({"/login"})
    @Ok("jsp:jsp.user.login")
    public void loginPage() {}

    @GET
    @At({"/", "/index"})
    @Ok("jsp:jsp.user.index")
    public void indexPage() {}

    @POST
    @At
    public NutMap login(String username, String password, HttpSession session) {
        NutMap re = new NutMap("ok", false);
        if (Strings.isBlank(username) || Strings.isBlank(password)) {
            log.debug("username or password is null");
            return re.setv("msg", "用户名或密码不能为空");
        }
        User user = dao.fetch(User.class, username);
        if (user == null) {
            log.debug("no such user = " + username);
            return re.setv("msg", "没有该用户");
        }
        //String tmp = Lang.digest("SHA-256", user.getSalt() + password);
        if (!password.equals(user.getPassword())) {
            log.debug("password is wrong");
            return re.setv("msg", "密码错误");
        }
        session.setAttribute("me", user);
        return re.setv("ok", true);
    }

}
