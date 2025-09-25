package com.gec.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.domain.entity.RegisterUser;

/**
 * 用户注册服务接口
 */
public interface IRegisterUserService extends IService<RegisterUser> {
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果，成功返回用户信息，失败返回null
     */
    RegisterUser login(String username, String password);
    
    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @return 注册结果，成功返回true，失败返回false
     */
    boolean register(String username, String password);
    
    /**
     * 检查用户名是否已存在
     * @param username 用户名
     * @return 存在返回true，不存在返回false
     */
    boolean checkUsernameExists(String username);
    
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息或null
     */
    RegisterUser findByUsername(String username);
}