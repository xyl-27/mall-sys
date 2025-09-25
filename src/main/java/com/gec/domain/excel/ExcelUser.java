package com.gec.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ExcelUser {

    @ExcelProperty("账号")
    private String account;

    @ExcelProperty("昵称")
    private String nickName;

    @ExcelProperty("手机号")
    private String phone;

    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty("工号")
    private String no;

    @ExcelProperty("部门ID")
    private Integer deptId;

    // 默认密码为123456
    private String password = "123456";

    private Date createDate = new Date();
    private Date updateDate = new Date();
}