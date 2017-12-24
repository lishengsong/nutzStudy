package com.gree.modules;

import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * @Author :Web寻梦狮（lishengsong）
 * @Date Created in 下午1:43 2017/12/24
 * @Description:
 */
public class MainModule {

    @At("/hello")
    @Ok("jsp:jsp.hello")
    public String doHello() {
        return "Hello Nutz";
    }
}
