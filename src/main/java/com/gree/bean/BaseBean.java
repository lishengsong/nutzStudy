package com.gree.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

import java.util.Date;

/**
 * @user: 180296-Web寻梦狮
 * @date: 2018-01-12 10:11
 * @description: 为了简化将来需要更多的Pojo(Bean),做个抽象的BasePojo,把共有的属性和方法统一一下
 */
public abstract class BaseBean {

    @Column("ct")
    @Prev(els = @EL("now()"))
    protected Date createTime;
    @Column("ut")
    protected Date updateTime;

    public String toString() {
        // 这不是必须的, 只是为了debug的时候方便看
        return Json.toJson(this, JsonFormat.compact());
        /**
         * Json.toJson(pet, JsonFormat.compact()); // 紧凑模式 -- 无换行,忽略null值
         * Json.toJson(pet, JsonFormat.full());    // 全部输出模式 -- 换行,不忽略null值
         * Json.toJson(pet, JsonFormat.nice());    // 一般模式 -- 换行,但忽略null值
         * Json.toJson(pet, JsonFormat.forLook()); // 为了打印出来容易看，把名字去掉引号
         * Json.toJson(pet, JsonFormat.tidy());    // 不换行,不忽略空值
        */

    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
