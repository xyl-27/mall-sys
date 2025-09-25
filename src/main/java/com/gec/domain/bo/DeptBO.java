package com.gec.domain.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gec.domain.entity.Dept;
import com.gec.domain.entity.Node;

import java.util.ArrayList;
import java.util.List;

public class DeptBO implements Node {
    private Integer id;
    private String deptName;     //dept_name
    private String deptDesc;     //dept_desc
    private Integer parentId;    //parent_id
    private String pIds;         //p_ids
    private Integer level;       // 部门层级
    private boolean hasSub;      // 是否有子部门
    
    // 显式添加所有需要的getter和setter方法
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getDeptDesc() {
        return deptDesc;
    }
    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public String getpIds() {
        return pIds;
    }
    public void setpIds(String pIds) {
        this.pIds = pIds;
    }

    // 修改为public并添加getter方法，确保Jackson能正确序列化
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<Node> children = new ArrayList<>();
    
    // 添加getter方法，让Jackson能够正确序列化children字段
    public List<Node> getChildren() {
        return children;
    }
    
    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public DeptBO() {}
    public DeptBO(Dept d) {
        if (d != null) {
            // 直接从Dept对象复制属性值
            this.id = d.getId();
            this.deptName = d.getDeptName();
            this.parentId = d.getParentId();
            this.pIds = d.getPIds();
            this.deptDesc = d.getDeptDesc();
            this.level = 0; // 默认层级为0
            this.hasSub = false; // 默认没有子部门
        }
    }
    
    // level属性的getter和setter
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    // hasSub属性的getter和setter
    public boolean getHasSub() {
        return hasSub;
    }
    
    public void setHasSub(boolean hasSub) {
        this.hasSub = hasSub;
    }
    public void addChildNode(Node node){
        children.add( node );
    }
}


