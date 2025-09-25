package com.gec.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.domain.bo.UserBO;
import com.gec.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IUserService extends IService<User> {

    IPage<UserBO> listUser(
            Page page, Map<String, Object> data);

    void saveUser(User user, Integer roleId);

    User getUser(Integer id);

    void deleteUser(Integer id);

    /**
     * 批量删除用户
     * @param ids 用户ID列表
     */
    void batchDeleteUser(List<Integer> ids);

    /**
     * 批量导入用户
     * @param file Excel文件
     * @param roleId 角色ID
     * @return 导入结果
     * @throws IOException 文件处理异常
     */
    Map<String, Object> batchImportUser(MultipartFile file, Integer roleId) throws IOException;
}
