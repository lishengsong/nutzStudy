package com.gree;

import org.nutz.mvc.annotation.Modules;

/**
 * @Author :Web寻梦狮（lishengsong）
 * @Date Created in 下午1:43 2017/12/24
 * @Description: 主入口模块
 */

import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;


@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/", "*anno",
        "com.gree", //搜索该包下的IocBean
        "*tx", // 事务拦截 aop
        "*async"}) // 异步执行aop
@Modules(scanPackage = true) ////1.r.58开始默认就是true
@SetupBy(value = MainSetup.class)
public class MainModule {



}
