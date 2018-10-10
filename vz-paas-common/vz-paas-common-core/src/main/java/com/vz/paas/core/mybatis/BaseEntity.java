package com.vz.paas.core.mybatis;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vz.paas.base.dto.LoginAuthDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 基础实体
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-10-10 20:24:48
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1110832777980608892L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "creator_id")
    private Long creatorId;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    /**
     * 最近操作人
     */
    @Column(name = "last_operator")
    private String lastOperator;

    /**
     * 最近操作人ID
     */
    @Column(name = "last_operator_id")
    private Long lastOperatorId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Transient
    private Integer pageNum;

    @Transient
    private Integer pageSize;

    @Transient
    private String orderBy;

    @Transient
    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }

    @Transient
    @JsonIgnore
    public void setUpdateInfo(LoginAuthDto loginUser) {
        if (isNew()) {
            this.creatorId = (this.lastOperatorId = loginUser.getUserId());
            this.creator = loginUser.getUserName();
            this.createdTime = (this.updateTime = new Date());
        }
        this.lastOperatorId = loginUser.getUserId();
        this.lastOperator = loginUser.getUserName() == null ? loginUser.getLoginName() : loginUser.getUserName();
        this.updateTime = new Date();
    }

}
