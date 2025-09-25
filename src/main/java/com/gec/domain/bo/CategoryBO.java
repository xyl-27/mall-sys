package com.gec.domain.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gec.domain.entity.BaseEntity;
import com.gec.domain.entity.Category;
import com.gec.domain.entity.Node;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

public class CategoryBO extends BaseEntity implements Node {
    private Integer id;
    private String categoryName;  //category_name
    private Integer parentId;     //parent_id
    private String pIds;          //
    private Integer showStatus;
    private Integer sort;

    private Integer level;   //BO 属性
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    // 显式添加所有需要的setter方法
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
    public Integer getShowStatus() {
        return showStatus;
    }
    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    private boolean hasSub = false;
    public boolean getHasSub(){
        if( children==null ) return false;
        return children.size() > 0;
    }

    // 修改为ALWAYS并添加getter方法，确保Jackson能正确序列化
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<Node> children = new ArrayList<>();
    
    // 添加getter方法，确保Jackson能正确序列化children列表
    public List<Node> getChildren() {
        return children;
    }
    
    // 添加setter方法，确保Jackson能正确反序列化children列表
    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public CategoryBO(){}
    public CategoryBO(Integer id, String categoryName, Integer parentId, String pIds, Integer showStatus, Integer sort, Integer level){
        this.id = id;
        this.categoryName = categoryName;
        this.parentId = parentId;
        this.pIds = pIds;
        this.showStatus = showStatus;
        this.sort = sort;
        this.level = level;
    }

    public void addChildNode(Node node){
        children.add( node );
    }
}
