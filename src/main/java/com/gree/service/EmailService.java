package com.gree.service;

/**
 * @Author :Web寻梦狮（lishengsong）
 * @Date Created in 下午9:57 2018/1/12
 * @Description:
 */
public interface EmailService {

    boolean send(String to, String subject, String html);
}
