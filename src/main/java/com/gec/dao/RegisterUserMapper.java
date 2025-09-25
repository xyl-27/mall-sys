package com.gec.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.domain.entity.RegisterUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户注册Mapper接口，用于操作tbl_register_user表
 */
@Mapper
public interface RegisterUserMapper extends BaseMapper<RegisterUser> {
    
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    RegisterUser findByUsername(String username);
}