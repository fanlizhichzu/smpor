package com.spring.smpor.mybatis.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.spring.smpor.common.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author fanlz
 * @since 2021-12-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("users")
@ApiModel(value = "UsersEntity对象", description = "")
public class UsersEntity extends PageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("account")
    private String account;

    @TableId(value = "account_id", type = IdType.ASSIGN_ID)
    private String accountId;

    @TableField("client_id")
    private String clientId;

    @TableField("employee_code")
    private String employeeCode;

    @TableField("last_name")
    private String lastName;

    @TableField("namespace")
    private String namespace;

    @TableField("nick_name_cn")
    private String nickNameCn;

    @TableField("realm_id")
    private String realmId;

    @TableField("realm_name")
    private String realmName;

    @TableField("tenant_id")
    private String tenantId;

    @TableField("tenant_name")
    private String tenantName;

    @TableField("tenant_user_id")
    private String tenantUserId;

    @TableField("divisioncode")
    private String divisioncode;

    @TableField("userorgname")
    private String userorgname;

    @TableField("mobile")
    private String mobile;

    @TableField("state")
    private Integer state;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;


}
