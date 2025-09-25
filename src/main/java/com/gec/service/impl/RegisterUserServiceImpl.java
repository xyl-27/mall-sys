package com.gec.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.dao.RegisterUserMapper;
import com.gec.domain.entity.RegisterUser;
import com.gec.service.IRegisterUserService;
import com.gec.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户注册服务实现类
 */
@Service
@Transactional
public class RegisterUserServiceImpl extends ServiceImpl<RegisterUserMapper, RegisterUser> implements IRegisterUserService {
    
    @Autowired
    private RegisterUserMapper registerUserMapper;
    
    @Override
    public RegisterUser login(String username, String password) {
        // 根据用户名查询用户
        RegisterUser user = registerUserMapper.findByUsername(username);
        if (user != null) {
            // 获取存储在数据库中的密码
            String storedPassword = user.getPassword();
            
            // 检查密码是否匹配 - 支持明文和加密两种情况
            if (password != null && 
                (password.equals(storedPassword) || 
                EncryptUtils.encrypt(password).equals(storedPassword))) {
                // 登录成功，返回用户信息（注意不要返回密码）
                RegisterUser result = new RegisterUser();
                result.setRtUserId(user.getRtUserId());
                result.setRtUsername(user.getRtUsername());
                result.setCreatedAt(user.getCreatedAt());
                result.setUpdatedAt(user.getUpdatedAt());
                return result;
            }
        }
        // 登录失败
        return null;
    }
    
    @Override
    public boolean register(String username, String password) {
        // 检查用户名是否已存在
        if (checkUsernameExists(username)) {
            return false;
        }
        
        // 创建新用户
        RegisterUser user = new RegisterUser();
        user.setRtUsername(username);
        // 直接存储原始密码，取消加密
        user.setPassword(password);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        
        // 保存用户信息
        return save(user);
    }
    
    @Override
    public boolean checkUsernameExists(String username) {
        RegisterUser user = registerUserMapper.findByUsername(username);
        return user != null;
    }
    
    @Override
    public RegisterUser findByUsername(String username) {
        return registerUserMapper.findByUsername(username);
    }
}