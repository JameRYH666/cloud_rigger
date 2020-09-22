package com.jeeadmin.api;


public interface ISendEmailService {

    /**
     * 发送邮箱验证码
     * @param to
     * @param subject
     */
    void sendEmailCode(String to, String subject);
}
