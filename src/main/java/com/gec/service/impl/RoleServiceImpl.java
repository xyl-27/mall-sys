package com.gec.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.domain.entity.Role;
import com.gec.dao.RoleMapper;
import com.gec.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RoleServiceImpl
    extends ServiceImpl<RoleMapper, Role>
    implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public void addUserRoleAssociation(Integer userId, Integer roleId) {
        // 在设置用户与角色关系之前，先撤消它们的关联
        removeUserRoleAssociation(userId);
        int cnt = roleMapper.addUserRoleAssociation(userId, roleId);
        if (cnt != 1) {
            throw new RuntimeException("设置用户与角色关联失败。");
        }
    }
    @Override
    public void removeUserRoleAssociation(Integer userId) {
        roleMapper.removeUserRoleAssociation(userId);
    }
    @Override
    public IPage<Role> listRole(
            Page page, Map<String, Object> data) {
            return null; }
    @Override
    public void deleteRole(Integer id) { }
    @Override
    public void addRole(Role role) { }
    @Override
    public void updateRole(Role role) { }
    @Override
    public Role getRole(Integer id) { return null; }
}