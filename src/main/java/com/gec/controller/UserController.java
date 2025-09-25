package com.gec.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.components.FileTemplate;
import com.gec.controller.R;
import com.gec.domain.entity.User;
import com.gec.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/User")
public class UserController extends BaseController {

    //1. 自动装配UserService接口
    @Autowired
    private IUserService userService;

    @PostMapping(
            value = "/list/{page}/{limit}",
            produces = "application/json;charset=UTF-8"
    )
    public R list(
            @PathVariable("page") Integer page,
            @PathVariable("limit") Integer limit,
            @RequestBody Map map
    ) {
        //1. 调用BaseController的封装页参数的方法。
        // (页面 + 页大小)
        Page _page = newPage(page, limit);



        IPage retPage = null;
        try {
            retPage = userService.listUser(_page, map);
            return R.convertPage(retPage);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    //2. 添加用户  addUser
    @PostMapping(
            value = "/addUser/{roleId}",
            produces = "application/json;charset=UTF-8"
    )
    public R addUser(
            @PathVariable("roleId")Integer roleId,
            @RequestBody User user
    ) {
        try {
            userService.saveUser(user, roleId);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    //3. 更新用户 updateUser
    @PutMapping(
            value = "/updateUser/{roleId}",
            produces = "application/json;charset=UTF-8"
    )
    public R updateUser(
            @PathVariable("roleId")Integer roleId,
            @RequestBody User user
    ) {
        try {
            userService.saveUser(user, roleId);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    //4. 获取用户
    @GetMapping("/getUserById/{id}")
    public R getUserById(@PathVariable("id") Integer id) {
        try {
            User user = userService.getUser(id);
            return R.ok().put("data", user);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    //5. 删除用户
    @DeleteMapping("/deleteUser/{id}")
    public R deleteUser(@PathVariable("id") Integer id) {
        try {
            userService.deleteUser(id);
            return R.ok().put("message", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    //6. 批量删除用户
    @DeleteMapping("/batchDelete")
    public R batchDeleteUser(@RequestBody List<Integer> ids) {
        try {
            userService.batchDeleteUser(ids);
            return R.ok().put("message", "批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    //7. 批量导入用户
    @PostMapping("/batchImport")
    public R batchImportUser(@RequestParam("file") MultipartFile file, 
                           @RequestParam("roleId") Integer roleId) {
        try {
            Map<String, Object> result = userService.batchImportUser(file, roleId);
            Integer failCount = (Integer) result.get("failCount");
            if (failCount != null && failCount > 0) {
                // 如果有失败记录，返回包含部分失败信息的成功响应
                return R.ok().put("message", "部分导入成功，有" + failCount + "条记录导入失败").put("result", result);
            } else {
                // 如果全部成功，返回成功响应
                return R.ok().put("message", "全部导入成功").put("result", result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return R.err(e);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    @Override
    protected FileTemplate getFileTemplate() {
        return null;
    }
}
