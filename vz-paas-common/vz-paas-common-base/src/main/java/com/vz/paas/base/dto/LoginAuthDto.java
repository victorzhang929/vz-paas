package com.vz.paas.base.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 安全登录数据传输对象
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-09-30 14:40:47
 */
@Data
@ApiModel(value = "登录人信息")
public class LoginAuthDto implements Serializable {
    private static final long serialVersionUID = -1245380057272628093L;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("登录名")
    private String loginName;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("组织ID")
    private Long groupId;

    @ApiModelProperty("组织名称")
    private String groupName;

    public LoginAuthDto() {}

    public LoginAuthDto(Long userId, String loginName, String userName) {
        this.userId = userId;
        this.loginName = loginName;
        this.userName = userName;
    }

    public LoginAuthDto(Long userId, String loginName, String userName, Long groupId, String groupName) {
        this.userId = userId;
        this.loginName = loginName;
        this.userName = userName;
        this.groupId = groupId;
        this.groupName = groupName;
    }
}
