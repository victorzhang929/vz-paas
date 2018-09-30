package com.vz.paas.base.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户密钥数据传输对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 15:04:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserTokenDto extends LoginAuthDto {
    private static final long serialVersionUID = 4230049933330369892L;

    private Long id;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    /**
     * 最后操作人
     */
    private String lastOperator;

    /**
     * 最后操作人ID
     */
    private Long lastOperatorId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 父ID
     */
    private Long pid;

    /**
     * 登录人IP地址
     */
    private String loginIp;

    /**
     * 登录位置
     */
    private String loginLocation;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 访问token
     */
    private String accessToken;

    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * 访问token的生效时间（秒）
     */
    private Integer accessTokenValidity;

    /**
     * 刷新token的生效时间（秒）
     */
    private Integer refreshTokenValidity;


    /**
     * 0 在线
     * 10 已刷新
     * 20 离线
     */
    private Integer status;
}
