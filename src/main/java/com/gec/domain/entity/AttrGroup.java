package com.gec.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName(value="tbl_goods_attr_group")
@EqualsAndHashCode(callSuper = false)
public class AttrGroup extends BaseEntity {
    @TableId(type= IdType.AUTO)
    private Integer id;            //id [自增长]
    
    @TableField("group_name")
    private String groupName;      //group_name
    
    @TableField("category_id")
    private Integer categoryId;    //category_id
    
    private String descript;       //描述
    
    private String icons;          //图标
    
    private Integer sort;          //排序

    //create_date [In BaseEntity]
    //update_date [In BaseEntity]
}
