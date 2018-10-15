package com.vz.paas.security.core;

import java.util.Collection;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 安全用户
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-11 09:42:39
 */
@Data
public class SecurityUser implements UserDetails {
    private static final long serialVersionUID = -1453747402384784488L;

    private static final String ENABLE = "ENABLE";

    /**
     * 认证集合
     */
    private Collection<GrantedAuthority> authorities;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 状态
     */
    private String status;

    /**
     * 组ID
     */
    private Long groupId;

    /**
     * 组名称
     */
    private String groupName;

    public SecurityUser(Long userId, String loginName, String loginPwd, String nickName, Long groupId, String groupName, String status, Collection<GrantedAuthority> authentications) {
        this.setUserId(userId);
        this.setLoginName(loginName);
        this.setLoginPwd(loginPwd);
        this.setNickName(nickName);
        this.setGroupId(groupId);
        this.setGroupName(groupName);
        this.setStatus(status);
        this.setAuthorities(authentications);
    }

    public SecurityUser(Long userId, String loginName, String loginPwd, String nickName, Long groupId, String groupName) {
        this.setUserId(userId);
        this.setLoginName(loginName);
        this.setLoginPwd(loginPwd);
        this.setNickName(nickName);
        this.setGroupId(groupId);
        this.setGroupName(groupName);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.getLoginPwd();
    }

    @Override
    public String getUsername() {
        return this.getLoginName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.equals(this.status, ENABLE);
    }
}
