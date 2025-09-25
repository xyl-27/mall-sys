package com.gec.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.dao.UserMapper;
import com.gec.domain.bo.UserBO;
import com.gec.domain.entity.User;
import com.gec.domain.excel.ExcelUser;
import com.gec.service.IRoleService;
import com.gec.service.IUserService;
import com.gec.utils.ExcelListener;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class UserServiceImpl
    extends ServiceImpl<UserMapper, User>
    implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IRoleService roleService;       

    @Override
    public IPage<UserBO> listUser(
        Page page, Map<String, Object> param) {
        Page<UserBO> retPage = userMapper.getList( page, param );
        return retPage;
    }

    @Override
    public void saveUser(User user, Integer roleId) {
        boolean ret = saveOrUpdate(user);
        if( !ret ){
            throw new RuntimeException("保存用户失败。");
        }
        // 使用反射获取userId，避免直接调用getter方法
        //Integer userId = 1; // 默认值作为临时解决方案
        // 正确的做法：直接使用getter方法获取保存后的用户ID
        Integer userId = user.getId();
        //2.用户与角色关联
        roleService.addUserRoleAssociation(
                userId, roleId );
    }

    @Override
    public User getUser(Integer id) {
        return this.getById(id);
    }

    @Override
    public void deleteUser(Integer id) {
        // 1. 先删除用户与角色的关联
        roleService.removeUserRoleAssociation(id);
        // 2. 删除用户信息
        this.removeById(id);
    }

    @Override
    public void batchDeleteUser(List<Integer> ids) {
        // 1. 先批量删除用户与角色的关联
        for (Integer id : ids) {
            roleService.removeUserRoleAssociation(id);
        }
        // 2. 批量删除用户信息
        this.removeBatchByIds(ids);
    }

    @Override
    public Map<String, Object> batchImportUser(MultipartFile file, Integer roleId) throws IOException {
        // 初始化结果统计
        int successCount = 0;
        int failCount = 0;
        List<String> failReasons = new ArrayList<>();
        List<ExcelUser> excelUsers = new ArrayList<>();
        
        // 检查文件类型
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new RuntimeException("请上传有效的文件");
        }
        
        // 根据文件类型选择不同的解析方式
        if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
            // 使用EasyExcel读取Excel文件
            ExcelListener listener = new ExcelListener();
            EasyExcel.read(file.getInputStream(), ExcelUser.class, listener).sheet().doRead();
            excelUsers = listener.getAllData();
        } else if (fileName.endsWith(".txt")) {
            // 读取TXT文件
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"))) {
                String line;
                int lineNum = 0;
                while ((line = reader.readLine()) != null) {
                    lineNum++;
                    // 跳过空行
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    
                    try {
                        // 同时支持英文逗号和中文逗号作为分隔符
                    String[] fields = line.split("[,，]");
                        if (fields.length < 6) {
                            failCount++;
                            failReasons.add("第" + lineNum + "行数据格式错误：字段数量不足");
                            continue;
                        }
                        
                        ExcelUser excelUser = new ExcelUser();
                        excelUser.setAccount(fields[0].trim());
                        excelUser.setNickName(fields[1].trim());
                        excelUser.setPhone(fields[2].trim());
                        excelUser.setSex(fields[3].trim());
                        excelUser.setNo(fields[4].trim());
                        try {
                            excelUser.setDeptId(Integer.parseInt(fields[5].trim()));
                        } catch (NumberFormatException e) {
                            failCount++;
                            failReasons.add("第" + lineNum + "行部门ID格式错误");
                            continue;
                        }
                        
                        excelUsers.add(excelUser);
                    } catch (Exception e) {
                        failCount++;
                        failReasons.add("第" + lineNum + "行数据解析失败：" + e.getMessage());
                    }
                }
            }
        } else {
            throw new RuntimeException("请上传Excel文件(xlsx或xls)或文本文件(txt格式)");
        }
        
        // 处理每一条数据
        for (ExcelUser excelUser : excelUsers) {
            try {
                // 转换ExcelUser为User
                User user = new User();
                BeanUtils.copyProperties(excelUser, user);
                
                // 保存用户并关联角色
                this.saveUser(user, roleId);
                successCount++;
            } catch (Exception e) {
                failCount++;
                failReasons.add("账号: " + excelUser.getAccount() + " 导入失败: " + e.getMessage());
            }
        }
        
        // 返回导入结果
        Map<String, Object> result = new HashMap<>();
        result.put("totalCount", excelUsers.size());
        result.put("successCount", successCount);
        result.put("failCount", failCount);
        result.put("failReasons", failReasons);
        
        return result;
    }
}
