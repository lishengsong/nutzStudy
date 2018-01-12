package com.gree.service;

import com.google.inject.Inject;
import org.nutz.dao.Dao;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-12 10:46
 * @description: BaseService 基础业务类
 */


public class BaseService {

    /** 注入同名的一个ioc对象 */
    @Inject
    protected Dao dao;
}
