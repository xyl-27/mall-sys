package com.gec.controller;
  
import com.gec.domain.entity.RegisterUser;
import com.gec.controller.R;
import com.gec.service.IRegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录控制器，处理用户登录、注册、注销和检查登录状态的请求
 */
@RestController
@RequestMapping("/api/auth")
public class LoginController {
    
    @Autowired
    private IRegisterUserService registerUserService;
    
    /**
     * 用户登录接口 - 支持多种请求格式
     * @param request 请求对象，用于获取参数
     * @param session HttpSession对象，用于存储用户登录状态
     * @return 登录结果
     */
    @PostMapping("/login")
    public R login(HttpServletRequest request, HttpSession session) {
        // 从请求中获取参数，这样可以支持多种请求格式
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return R.ok().put("result", "failed").put("message", "用户名或密码不能为空");
        }
        
        // 调用服务层进行登录验证
        RegisterUser user = registerUserService.login(username, password);
        if (user != null) {
            // 登录成功，将用户信息存入session
            session.setAttribute("user", user);
            return R.ok().put("message", "登录成功").put("user", user);
        } else {
            // 登录失败
            return R.ok().put("result", "failed").put("message", "用户名或密码错误");
        }
    }
    
    /**
     * 用户注册接口 - 支持多种请求格式
     * @param request 请求对象，用于获取参数
     * @return 注册结果
     */
    @PostMapping("/register")
    public R register(HttpServletRequest request) {
        // 从请求中获取参数，这样可以支持多种请求格式
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return R.ok().put("result", "failed").put("message", "用户名或密码不能为空");
        }
        
        // 调用服务层进行注册
        boolean success = registerUserService.register(username, password);
        if (success) {
            return R.ok().put("message", "注册成功");
        } else {
            return R.ok().put("result", "failed").put("message", "用户名已存在");
        }
    }
    
    /**
     * 用户注销接口
     * @param session HttpSession对象，用于移除用户登录状态
     * @return 注销结果
     */
    @PostMapping("/logout")
    public R logout(HttpSession session) {
        // 移除session中的用户信息
        session.removeAttribute("user");
        return R.ok().put("message", "注销成功");
    }
    
    /**
     * 检查用户登录状态接口
     * @param session HttpSession对象，用于获取用户登录状态
     * @return 登录状态
     */
    @GetMapping("/checkLogin")
    public R checkLogin(HttpSession session) {
        // 从session中获取用户信息
        RegisterUser user = (RegisterUser) session.getAttribute("user");
        if (user != null) {
            // 用户已登录
            return R.ok().put("isLogin", true).put("user", user);
        } else {
            // 用户未登录
            return R.ok().put("isLogin", false);
        }
    }
    
    // 添加查询用户是否存在的接口，用于验证注册是否成功
    @GetMapping("/checkUser")
    public R checkUser(String username) {
        if (username == null || username.isEmpty()) {
            return R.ok().put("result", "failed").put("message", "用户名不能为空");
        }
        
        RegisterUser user = registerUserService.findByUsername(username);
        if (user != null) {
            return R.ok().put("exists", true).put("userId", user.getRtUserId());
        } else {
            return R.ok().put("exists", false);
        }
    }
}