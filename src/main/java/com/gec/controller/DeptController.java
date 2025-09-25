package com.gec.controller;

import com.gec.components.FileTemplate;
import com.gec.service.IDeptService;
import com.gec.domain.entity.Dept;
import com.gec.domain.entity.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/Dept")
public class DeptController
        extends BaseController {
    //1.自动装配--DeptService
    @Autowired
    private IDeptService deptService;

    //1. 部门列表（树形结构）
    //测试地址：http://localhost:8090/mall-sys/Dept/list
    @GetMapping(value="/list", produces = "application/json;charset=UTF-8")
    public R list(){
        List<Node> list = null;
        try{
            //1.调用 service 去查询列表。
            list = deptService.listDept();
            //2.返回json格式数据。
            return R.ok(list);
        }catch (Exception e){
            e.printStackTrace();
            return R.err(e);
        }
    }

    //2.添加部门   POST请求
    @PostMapping(value="/addDept")
    public R addDept(@RequestBody Dept dept){
        deptService.addDept( dept );
        return R.ok();
    }

    //3.更新部门   PUT请求
    @PutMapping(value="/updateDept")
    public R updateDept(@RequestBody Dept dept){
        deptService.updateDept( dept );
        return R.ok();
    }

    //4. 获取部门详情
    @GetMapping(value="/getDept/{id}")
    public R getDept(@PathVariable("id") Integer id){
        try{
            Dept dept = deptService.getDept(id);
            return R.ok(dept);
        }catch (Exception e){
            e.printStackTrace();
            return R.err(e);
        }
    }

    //5.删除部门-DELETE请求。
    @DeleteMapping(value="/deleteDept/{id}")
    public R deleteDept(
        @PathVariable("id") Integer id){
        try {
            //1.调用service执行删除操作
            deptService.deleteDept(id);
            //2.返回R对象，并转换为json数据，给前端
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }
    
    //6. 检查部门是否有子部门（用于树形菜单显示判断）
    @GetMapping(value="/checkHasSubDept/{id}")
    public R checkHasSubDept(@PathVariable("id") Integer id) {
        try {
            boolean hasSubDept = deptService.checkHasSubDept(id);
            Map<String, Boolean> result = new HashMap<>();
            result.put("hasSubDept", hasSubDept);
            return R.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }
    
    //7. 检查部门是否为4级部门（用于树形菜单显示判断）
    @GetMapping(value="/checkIsFourthLevelDept/{id}")
    public R checkIsFourthLevelDept(@PathVariable("id") Integer id) {
        try {
            boolean isFourthLevel = deptService.checkIsFourthLevelDept(id);
            Map<String, Boolean> result = new HashMap<>();
            result.put("isFourthLevel", isFourthLevel);
            return R.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e);
        }
    }

    @ExceptionHandler
    public void exceptionHandler(
        Exception e, HttpServletResponse resp){
        R.err( e ).write( resp );
    }

    @Override
    protected FileTemplate getFileTemplate() {
        return null;
    }
}
