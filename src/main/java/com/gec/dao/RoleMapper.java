package com.gec.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.domain.entity.Dept;
import com.gec.domain.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper extends BaseMapper<Role> {

    /* ps:这里要空格。*/
    @Insert(
            "insert into tbl_user_role(user_id,role_id) "+
                    " values(#{userId},#{roleId})"
    )
    int addUserRoleAssociation(
            @Param("userId") Integer userId,
            @Param("roleId") Integer roleId);

    /* ps:这里要空格。*/
    @Delete("delete from tbl_user_role "+" where user_id=#{userId}")
    int removeUserRoleAssociation(
            @Param("userId") Integer userId);

}



