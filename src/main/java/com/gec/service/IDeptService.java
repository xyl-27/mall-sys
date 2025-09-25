package com.gec.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.domain.entity.Node;
import com.gec.domain.entity.Dept;

import java.util.List;

public interface IDeptService extends IService<Dept> {

    List<Node> listDept();

    void deleteDept(Integer id);

    void addDept(Dept dept);

    void updateDept(Dept dept);

    Dept getDept(Integer id);
    
    /**
     * 检查部门是否有子部门（用于树形菜单显示判断）
     */
    boolean checkHasSubDept(Integer deptId);
    
    /**
     * 检查部门是否为4级部门（用于树形菜单显示判断）
     */
    boolean checkIsFourthLevelDept(Integer deptId);

}



