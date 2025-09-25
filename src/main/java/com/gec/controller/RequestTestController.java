package com.gec.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于测试HTTP请求是否能正确到达后端，以及请求参数是否能被正确获取
 */
@RestController
@RequestMapping("/test")
public class RequestTestController {

    /**
     * 测试GET请求
     */
    @GetMapping("/get")
    public Map<String, Object> testGet(HttpServletRequest request) {
        System.out.println("收到GET请求");
        System.out.println("请求URL: " + request.getRequestURL());
        System.out.println("查询参数: " + request.getQueryString());
        
        // 获取所有请求参数
        Map<String, Object> params = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            params.put(paramName, paramValue);
            System.out.println("参数名: " + paramName + ", 参数值: " + paramValue);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "GET请求测试成功");
        response.put("receivedParams", params);
        
        return response;
    }

    /**
     * 测试POST表单请求
     */
    @PostMapping("/post-form")
    public Map<String, Object> testPostForm(HttpServletRequest request) {
        System.out.println("收到POST表单请求");
        System.out.println("请求URL: " + request.getRequestURL());
        System.out.println("Content-Type: " + request.getContentType());
        
        // 获取所有请求参数
        Map<String, Object> params = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            params.put(paramName, paramValue);
            System.out.println("参数名: " + paramName + ", 参数值: " + paramValue);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "POST表单请求测试成功");
        response.put("receivedParams", params);
        
        return response;
    }

    /**
     * 测试登录请求参数获取
     */
    @PostMapping("/test-login-params")
    public Map<String, Object> testLoginParams(HttpServletRequest request) {
        System.out.println("测试登录参数获取");
        System.out.println("Content-Type: " + request.getContentType());
        
        // 尝试获取username和password参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        System.out.println("username参数: " + username);
        System.out.println("password参数: " + password);
        System.out.println("username是否为空: " + (username == null || username.isEmpty()));
        System.out.println("password是否为空: " + (password == null || password.isEmpty()));
        
        // 获取所有请求参数
        Map<String, Object> allParams = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            allParams.put(paramName, request.getParameter(paramName));
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("usernameReceived", username);
        response.put("passwordReceived", password);
        response.put("allParams", allParams);
        response.put("message", "登录参数测试完成");
        
        return response;
    }
}