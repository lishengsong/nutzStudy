package com.gree.modules;

import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * @Author :Web寻梦狮（lishengsong）
 * @Date Created in 下午11:10 2018/1/5
 * @Description:
 */
@At("/index")
public class IndexModule {

    @At("/index")
    @Ok("jsp:jsp.hello")
    public String doHello() {
        return "index啊啊啊";
    }
}
