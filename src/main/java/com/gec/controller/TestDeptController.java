package com.gec.controller;

import com.gec.domain.entity.Node;
import com.gec.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/testDept")
public class TestDeptController {

    @Autowired
    private IDeptService deptService;

    // 直接返回树形结构数据，用于测试
    @GetMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public List<Node> testListDept() {
        System.out.println("===== 测试部门列表树形结构 =====");
        List<Node> deptList = deptService.listDept();
        System.out.println("部门数量: " + deptList.size());
        return deptList;
    }
}