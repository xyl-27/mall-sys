
package com.gec.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 用户注册实体类，对应tbl_register_user表
 */
@Data
@TableName("tbl_register_user")
public class RegisterUser {
    
    @TableId(type = IdType.AUTO)
    private Integer rtUserId;
    
    private String rtUsername;
    
    private String password;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdAt;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updatedAt;
}