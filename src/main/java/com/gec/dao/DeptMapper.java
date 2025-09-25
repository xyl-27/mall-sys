package com.gec.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.domain.entity.Dept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface DeptMapper extends BaseMapper<Dept> {
    //{1}获取某部门的用户数量.（where 前要有空格)
    @Select("select count(*) from tbl_user u "+" where u.dept_id=#{deptId}")
    int getUserCount(@Param("deptId") Integer deptId);
    //{2}获取子部门的数量（某个部门下有几个一级下属部门)
    // (where 前要有空格)
    @Select("select count(*) from tbl_dept d "+" where d.parent_id=#{deptId}")
    int getSubDeptCount(@Param("deptId") Integer deptId);
    
    // 检查部门是否存在子部门
    int hasSubDept(@Param("deptId") Integer deptId);
    
    // 检查部门是否有用户
    int hasUser(@Param("deptId") Integer deptId);
    
    // 获取部门的层级深度
    int getDeptLevel(@Param("deptId") Integer deptId);
    
    // 根据ID获取部门信息
    Dept getDeptById(@Param("deptId") Integer deptId);
}



