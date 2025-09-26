package com.gec.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.domain.entity.User;
import com.gec.domain.bo.UserBO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    Page<UserBO> getList(
         Page page,
         @Param("param") Map<String,Object> param );

    @Insert("INSERT INTO tbl_user(account, nick_name, phone, sex, no, dept_id, password, create_date, update_date) " +
            "VALUES(#{account}, #{nickName}, #{phone}, #{sex}, #{no}, #{deptId}, #{password}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User user);

}
