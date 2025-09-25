package com.gec.domain.bo;

import com.gec.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SaleAttrBO extends BaseEntity {
    private Integer id;           //id
    private String attrName;      //brand_name

    private Integer attrType;      //1:基本属性 2:销售属性
    private String  attrTypeName;  //[BO Attr]

    private Integer valueType;      //1:单值 | 2:多值
    private String  valueTypeName;  //单值 | 多值

    private String  attrValue;     //
    private Integer enable;       //
    private Integer categoryId;   //

    private Integer searchEnable;   //

    private Integer attrGroupId; //RA.attr_group_id
    private String  groupName;   //g.group_name

    public SaleAttrBO() {}

    public String getAttrTypeName(){
        if( attrType==null ) return null;
        String typeName = (this.attrType==1) ? "基本属性" : "销售属性";
        return typeName;
    }

    public String getValueTypeName(){
        if( valueType==null ) return null;
        String typeName = (this.valueType==1) ? "单值型" : "多值型";
        return typeName;
    }
}