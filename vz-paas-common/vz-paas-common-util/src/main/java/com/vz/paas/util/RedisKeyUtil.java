package com.vz.paas.util;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

/**
 * Redis键工具类
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-08 18:02:59
 */
public class RedisKeyUtil {

    private static final String RESET_PWD_TOKEN_KEY = "paas:restPwd";
    private static final String ACTIVE_USER = "paas:activeUser";
    private static final String SEND_SMS_COUNT = "paas:sms:count";
    private static final String SEND_EMAIL_CODE = "paas:email:code";
    private static final String ACCESS_TOKEN = "paas:token:accessToken";
    private static final String UPLOAD_FILE_SIZE = "paas:file:upload_file_size";
    private static final int REF_NO_MAX_LENGTH = 100;

    /**
     * 获取重置密码编码
     * @param resetPwdTokenKey 重置密码令牌
     * @return 重置密码编码
     */
    public static String getResetPwdTokenKey(String resetPwdTokenKey) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(resetPwdTokenKey), "重置密码令牌不能为空");
        return RESET_PWD_TOKEN_KEY + ":" + resetPwdTokenKey;
    }

    /**
     * 获取发送邮件编码
     * @param loginName 用户名
     * @param email 邮件
     * @return 发送邮件编码
     */
    public static String getSendEmailCode(String loginName, String email) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(loginName), "用户名不能为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(email), "邮箱不能为空");
        return SEND_EMAIL_CODE + ":" + loginName + email;
    }

    /**
     * 获取激活用户编码
     * @param activeUser 激活用户编码
     * @return 用户编码
     */
    public static String getActiveUser(String activeUser) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(activeUser), "激活用户参数不能为空");
        return ACTIVE_USER + ":" + activeUser;
    }

    /**
     * 获取发送消息编码
     * @param ip 地址
     * @param type 类型
     * @return 消息编码
     */
    public static String getSendSmsCount(String ip, String type) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(ip), "请不要篡改IP地址");
        return SEND_SMS_COUNT + ":" + type + ip;
    }

    /**
     * 获取发送消息频率编码
     * @param ip 地址
     * @return 消息频率编码
     */
    public static String getSendSmsRate(String ip) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(ip), "请不要篡改IP地址");
        return SEND_SMS_COUNT + ":" + ip;
    }

    /**
     * 获取请求编码
     * @param token 令牌
     * @return 请求编码
     */
    public static String getAccessToken(String token) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(token), "非法请求token参数不存在");
        return ACCESS_TOKEN + ":" + token;
    }

    /**
     * 创建消息队列编码
     * @param topic 主题
     * @param tag 标签
     * @param refNo 关联
     * @param body 消息体
     * @return 消息队列编码
     */
    public static String createMqKey(String topic, String tag, String refNo, String body) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(topic), "topic is null");
        Preconditions.checkArgument(StringUtils.isNotEmpty(tag), "tag is null");
        Preconditions.checkArgument(StringUtils.isNotEmpty(refNo), "refNo is null");
        Preconditions.checkArgument(StringUtils.isNotEmpty(body), "body is null");
        String temp = refNo;

        if (refNo.length() > REF_NO_MAX_LENGTH) {
            temp = StringUtils.substring(refNo, 0, REF_NO_MAX_LENGTH) + "...";
        }
        return topic + "_" + "tag" + "_" + "temp" + "-" + body.hashCode();
    }

    /**
     * 获取文件大小编码
     * @return 文件大小编码
     */
    public static String getFileSizeKey() {
        return UPLOAD_FILE_SIZE;
    }
}
