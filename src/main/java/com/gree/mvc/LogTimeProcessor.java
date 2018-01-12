package com.gree.mvc;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-12 09:07
 * @description: LogTimeProcessor 继承org.nutz.mvc.impl.processor.AbstractProcessor
 * 然后打开MainModule这个类,加入一个注解
 * @ChainBy(args="mvc/mvc-chain.js") 启动tomcat并登陆登出,可以看到有类似的log输出
 * 2015-04-09 19:46:59,140 com.gree.mvc.LogTimeProcessor.process(LogTimeProcessor.java:27)
 * DEBUG - [POST]URI=/nutzbook/user/login 30ms
 * @Aimt : 这个类的主要目的是演示动作链的配置, 因为druid的监控页面有更详尽的统计数据了(以前可没有druid,呵呵)
 */
import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Stopwatch;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.AbstractProcessor;

public class LogTimeProcessor extends AbstractProcessor {

    private static final Log log = Logs.get();

    public void process(ActionContext ac) throws Throwable {
        Stopwatch sw = Stopwatch.begin();
        try {
            doNext(ac);
        } finally {
            sw.stop();
            if (log.isDebugEnabled()) {
                HttpServletRequest req = ac.getRequest();
                log.debugf("[%4s]URI=%s %sms", req.getMethod(), req.getRequestURI(), sw.getDuration());
            }
        }
    }

}
