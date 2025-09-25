package com.gec.domain.vo;

import com.gec.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AttrGroupVO extends BaseEntity {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer id;            //id [自增长]
    
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String groupName;      //group_name
    
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer categoryId;    //category_id
    
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String descript;       //描述
    
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String icons;          //图标
    
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer sort;          //排序
}