package com.gec.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.controller.R;
import com.gec.domain.entity.User;
import com.gec.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private IUserService userService;

    @GetMapping("/simpleList")
    public R simpleList() {
        try {
            // 使用MyBatis-Plus的基础方法，不使用自定义SQL
            Page<User> page = new Page<>(1, 10);
            IPage<User> result = userService.page(page);
            return R.convertPage(result);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }
}