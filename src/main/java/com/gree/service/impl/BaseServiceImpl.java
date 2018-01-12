package com.gree.service.impl;

import com.google.inject.Inject;
import org.nutz.dao.Dao;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-12 10:46
 * @description: BaseServiceImpl 基础业务类
 */


public class BaseServiceImpl {

    /** 注入同名的一个ioc对象 */
    @Inject
    protected Dao dao;



}
