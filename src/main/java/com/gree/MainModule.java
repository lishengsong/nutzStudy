package com.gree;

import org.nutz.mvc.annotation.*;

/**
 * @Author :Web寻梦狮（lishengsong）
 * @Date Created in 下午1:43 2017/12/24
 * @Description: 主入口模块
 */

import org.nutz.mvc.ioc.provider.ComboIocProvider;


@ChainBy(args="mvc/mvc-chain.js")
@Fail("jsp:jsp.500")
@Localization(value="language/", defaultLocalizationKey="zh-CN")
@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/", "*anno",
        "com.gree", //搜索该包下的IocBean
        "*tx", // 事务拦截 aop
        "*quartz"}) // 即添加了 org.nutz.integration.quartz.QuartzIocLoader 这个预定义的集成配置
@Modules(scanPackage = true) ////1.r.58开始默认就是true
@SetupBy(value = MainSetup.class)
public class MainModule {



}
