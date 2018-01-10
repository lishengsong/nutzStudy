/**
 *@user: 180296-Web寻梦狮
 *@date: 2018-01-10 16:36
 *@description: dao.js
 */

/*
var ioc = {
    dataSource : {
        type : "com.alibaba.druid.pool.DruidDataSource",
        events : {
            create : "init",
            depose : 'close'
        },
        fields : {
            url : "jdbc:mysql://127.0.0.1:3306/nutz_study",
            username : "root",
            password : "",
            testWhileIdle : true, // 非常重要,预防mysql的8小时timeout问题
            //validationQuery : "select 1" , // Oracle的话需要改成 select 1 from dual
            maxWait: 15000, // 若不配置此项,如果数据库未启动,druid会一直等可用连接,卡住启动过程
            maxActive : 100
        }
    },
    dao : {
        type : "org.nutz.dao.impl.NutDao",
        args : [{refer:"dataSource"}]
    }
};*/
var ioc = {
    conf : {
        type : "org.nutz.ioc.impl.PropertiesProxy",
        fields : {
            paths : ["custom/"]
        }
    },
    dataSource : {
        factory : "$conf#make",
        args : ["com.alibaba.druid.pool.DruidDataSource", "db."],
        type : "com.alibaba.druid.pool.DruidDataSource",
        events : {
            create : "init",
            depose : 'close'
        }
    },
    dao : {
        type : "org.nutz.dao.impl.NutDao",
        args : [{refer:"dataSource"}],
        fields : {
            //executor : {refer: "cacheExecutor"},
            //runner : {refer: "daoRunner"},
            //interceptors : [{refer:"cacheExecutor"}, "log", "time"]
        }
    }
};