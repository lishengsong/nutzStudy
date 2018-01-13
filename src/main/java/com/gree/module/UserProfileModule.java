package com.gree.module;

import com.gree.bean.User;
import com.gree.bean.UserProfile;
import com.gree.service.impl.EmailServiceImpl;
import com.gree.util.Toolkit;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.DaoException;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.util.Daos;
import org.nutz.img.Images;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.Scope;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.*;

import org.nutz.mvc.filter.CheckSession;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;
import java.util.Date;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-12 11:24
 * @description:
 */
@IocBean
@At("/user/profile")
@Filters(@By(type = CheckSession.class, args = {"me", "/user/login"})) //含义是,如果当前Session没有带me这个attr,就跳转到/页面,即首页.
public class UserProfileModule {

    private static final Log log = Logs.get();

    @Inject
    protected Dao dao; // 就这么注入了,有@IocBean它才会生效

    @Inject
    protected EmailServiceImpl emailService;

    protected byte[] emailKEY = R.sg(24).next().getBytes();

    @At("/")
    @GET
    @Ok("jsp:jsp.user.profile")
    public UserProfile index(@Attr(scope=Scope.SESSION, value="me")User user) {
        return get(user);
    }


    @At
    public UserProfile get(@Attr(scope= Scope.SESSION, value="me")User user) {
        UserProfile profile = Daos.ext(dao, FieldFilter.locked(UserProfile.class, "avatar")).fetch(UserProfile.class, user.getId());
        if (profile == null) {
            profile = new UserProfile();
            profile.setUserId(user.getId());
            profile.setUpdateTime(new Date());
            dao.insert(profile);
        }
        return profile;
    }

    @At
    @AdaptBy(type=JsonAdaptor.class) //前端发送来的数据必须是
    @Ok("void")
    public void update(@Param("..")UserProfile profile, @Attr(scope=Scope.SESSION, value="me")User user) {
        if (profile == null)
            return;
        profile.setUserId(user.getId());//修正userId,防止恶意修改其他用户的信息
        profile.setUpdateTime(new Date());
        profile.setAvatar(null); // 不准通过这个方法更新
        UserProfile old = get(user);
        // 检查email相关的更新
        if (old.getEmail() == null) {
            // 老的邮箱为null,所以新的肯定是未check的状态
            profile.setEmailChecked(false);
        } else {
            if (profile.getEmail() == null) {
                profile.setEmail(old.getEmail());
                profile.setEmailChecked(old.isEmailChecked());
            } else if (!profile.getEmail().equals(old.getEmail())) {
                // 设置新邮箱,果断设置为未检查状态
                profile.setEmailChecked(false);
            } else {
                profile.setEmailChecked(old.isEmailChecked());
            }
        }
        Daos.ext(dao, FieldFilter.create(UserProfile.class, null, "avatar", true)).update(profile);
    }


    /**
     * 头像上传
     * @param tf
     * @param user
     * @param err
     * Upload适配器的几个参数: 临时文件夹路径,缓存环的大小,编码,临时文件夹的文件数,上传文件的最大大小
     */
    @AdaptBy(type=UploadAdaptor.class, args={"${app.root}/WEB-INF/tmp/user_avatar", "8192", "utf-8", "20000", "102400"})
    @POST
    @Ok("json")
    @At("/avatar")
    public NutMap uploadAvatar(@Param("file")TempFile tf,
                               @Attr(scope=Scope.SESSION, value="me") User user,
                               AdaptorErrorContext err) {
        String msg = null;
        if (err != null && err.getAdaptorErr() != null) {
            msg = "文件大小不符合规定";
        } else if (tf == null) {
            msg = "空文件";
        } else {
            UserProfile profile = get(user);
            try {
                BufferedImage image = Images.read(tf.getFile());
                image = Images.zoomScale(image, 128, 128, Color.WHITE);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Images.writeJpeg(image, out, 0.8f);
                profile.setAvatar(out.toByteArray());
                dao.update(profile, "^avatar$");
            } catch(DaoException e) {
                log.info("System Error", e);
                msg = "系统错误";
            } catch (Throwable e) {
                msg = "图片格式错误";
            }
        }
        NutMap map = new NutMap("ok",false);
        if (msg != null) {

            Mvcs.getHttpSession().setAttribute("upload-error-msg", msg);
            return  map.setv("msg",msg);
        }
        else return map.setv("ok",true);
            //Mvcs.getHttpSession().setAttribute("upload-error-msg", msg);

    }


    @Ok("raw:jpg")
    @At("/readAvatar")
    @GET
    public Object readAvatar(@Attr(scope=Scope.SESSION, value="me")User user, HttpServletRequest req) throws SQLException {
        UserProfile profile = Daos.ext(dao, FieldFilter.create(UserProfile.class, "^avatar$")).fetch(UserProfile.class, user.getId());
        if (profile == null || profile.getAvatar() == null) {
            return new File(req.getServletContext().getRealPath("/rs/user_avatar/none.jpg"));
        }
        return profile.getAvatar();
    }

    @At("/active/mail")
    @POST
    public Object activeMail(@Attr(scope=Scope.SESSION, value="me")User user,
                             HttpServletRequest req) {
        NutMap re = new NutMap();
        UserProfile profile = get(user);
        if (Strings.isBlank(profile.getEmail())) {
            return re.setv("ok", false).setv("msg", "你还没有填邮箱啊!");
        }
        String token = String.format("%s,%s,%s", user.getId(), profile.getEmail(), System.currentTimeMillis());
        token = Toolkit._3DES_encode(emailKEY, token.getBytes());
        String url = req.getRequestURL() + "?token=" + token;
        String html = "<div>如果无法点击,请拷贝一下链接到浏览器中打开<p/>验证链接 %s</div>";
        html = String.format(html, url, url);
        try {
            boolean ok = emailService.send(profile.getEmail(), "XXX 验证邮件 by Nutzbook", html);
            if (!ok) {
                return re.setv("ok", false).setv("msg", "发送失败");
            }
        } catch (Throwable e) {
            log.debug("发送邮件失败", e);
            return re.setv("ok", false).setv("msg", "发送失败");
        }
        return re.setv("ok", true);
    }

    @Filters // 不需要先登录,很明显...
    @At("/active/mail")
    @GET
    @Ok("raw") // 为了简单起见,这里直接显示验证结果就好了
    public String activeMailCallback(@Param("token")String token, HttpSession session) {
        if (Strings.isBlank(token)) {
            return "请不要直接访问这个链接!!!";
        }
        if (token.length() < 10) {
            return "非法token";
        }
        try {
            token = Toolkit._3DES_decode(emailKEY, Toolkit.hexstr2bytearray(token));
            if (token == null)
                return "非法token";
            String[] tmp = token.split(",", 3);
            if (tmp.length != 3 || tmp[0].length() == 0 || tmp[1].length() == 0 || tmp[2].length() == 0)
                return "非法token";
            long time = Long.parseLong(tmp[2]);
            if (System.currentTimeMillis() - time > 10*60*1000) {
                return "该验证链接已经超时";
            }
            int userId = Integer.parseInt(tmp[0]);
            Cnd cnd = Cnd.where("userId", "=", userId).and("email", "=", tmp[1]);
            int re = dao.update(UserProfile.class, Chain.make("emailChecked", true), cnd);
            if (re == 1) {
                return "验证成功";
            }
            return "验证失败!!请重新验证!!";
        } catch (Throwable e) {
            log.debug("检查token时出错", e);
            return "非法token";
        }
    }
}
