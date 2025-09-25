package com.gec.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.dao.DeptMapper;
import com.gec.domain.bo.DeptBO;
import com.gec.domain.entity.Dept;
import com.gec.domain.entity.Node;
import com.gec.service.IBaseService;
import com.gec.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeptServiceImpl
        extends ServiceImpl<DeptMapper, Dept>
        implements IDeptService, IBaseService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Node> listDept() {
        //1.mybatis-plus 条件设置器
        QueryWrapper<Dept> WR = new QueryWrapper<>();
        //2.查询部门列表
        List<Dept> depts = deptMapper.selectList(WR);
        
        //3.直接调用通用接口方法来转换层级结构化数据
        List<Node> listBO = convertNodeBO(depts);
        
        //4.返回 listBO
        return listBO;
    }

    //主要是为了适配不同类型
    @Override
    public Node copyObj(Node N) {
        // 当传入的是Dept对象时，使用构造函数创建DeptBO对象，确保children列表被正确初始化
        if (N instanceof Dept) {
            Dept dept = (Dept)N;
            return new DeptBO(dept);
        } 
        // 当传入的是DeptBO对象时，创建一个新的DeptBO对象
        else if (N instanceof DeptBO) {
            DeptBO source = (DeptBO)N;
            DeptBO deptBO = new DeptBO();
            deptBO.setId(source.getId());
            deptBO.setDeptName(source.getDeptName());
            deptBO.setDeptDesc(source.getDeptDesc());
            deptBO.setParentId(source.getParentId());
            deptBO.setpIds(source.getpIds());
            deptBO.setLevel(source.getLevel());
            deptBO.setHasSub(source.getHasSub());
            return deptBO;
        }
        
        return null;
    }

    @Override
    public void deleteDept(Integer deptId) {
        //1.检查部门是否存在
        Dept dept = getById(deptId);
        if (dept == null) {
            throw new RuntimeException("部门不存在");
        }
        
        //2.检查部门是否有用户
        int userCount = deptMapper.hasUser(deptId);
        if (userCount > 0) {
            throw new RuntimeException("此部门存在用户关联，不能删除");
        }
        
        //3.检查部门是否有子部门
        int subDeptCount = deptMapper.hasSubDept(deptId);
        if (subDeptCount > 0) {
            throw new RuntimeException("此部门存在子部门关联，不能删除");
        }

        //4.执行删除操作
        boolean ret = removeById(deptId);
        if (!ret) {
            throw new RuntimeException("删除部门失败");
        }
    }

    /**
     * 设置父部门信息，包括pIds字段
     */
    private Dept setParentId(Dept dept) {
        try {
            Integer parentId = dept.getParentId();
            
            if (parentId != null && parentId > 0) {
                Dept parentDept = getById(parentId);
                if (parentDept != null) {
                    String pIds = parentDept.getPIds();
                    
                    // 构建新的pIds
                    if (pIds != null) {
                        dept.setPIds(pIds + "," + parentId);
                    } else {
                        dept.setPIds("0," + parentId);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("设置父部门信息失败", e);
        }
        return dept;
    }

    @Override
    public void addDept(Dept dept) {
        //1.验证父部门是否存在
        Integer parentId = dept.getParentId();
        if (parentId != null && parentId > 0) {
            Dept parentDept = getById(parentId);
            if (parentDept == null) {
                throw new RuntimeException("父部门不存在");
            }
            
            //2.检查父部门层级，确保不超过4级
            int parentLevel = deptMapper.getDeptLevel(parentId);
            if (parentLevel >= 3) { // 0,1,2,3 表示4级
                throw new RuntimeException("部门层级不能超过4级");
            }
        }
        
        //3.设置父部门信息
        Dept _dept = setParentId(dept);
        
        //4.执行添加操作
        boolean ret = save(_dept);
        if (!ret) {
            throw new RuntimeException("添加部门失败");
        }
    }

    @Override
    public void updateDept(Dept dept) {
        //1.验证部门是否存在
        Dept existingDept = getById(dept.getId());
        if (existingDept == null) {
            throw new RuntimeException("部门不存在");
        }
        
        //2.不能变更父节点ID关联 - 修复：当新父节点ID为null时使用现有父节点ID
        Integer existingParentId = existingDept.getParentId();
        Integer newParentId = dept.getParentId();
        System.out.println("调试信息: 部门ID=" + dept.getId() + ", 现有父节点ID=" + existingParentId + ", 新父节点ID=" + newParentId);
        
        // 如果新父节点ID为null，使用现有的父节点ID
        if (newParentId == null) {
            dept.setParentId(existingParentId);
            newParentId = existingParentId;
            System.out.println("调试信息: 新父节点ID为null，已使用现有父节点ID: " + existingParentId);
        }
        
        // 再次检查父节点ID是否发生变化
        if ((existingParentId == null && newParentId != null) || 
            (existingParentId != null && !existingParentId.equals(newParentId))) {
            throw new RuntimeException("编辑部门时不能变更父节点ID关联");
        }
        
        //3.执行更新操作
        boolean ret = updateById(dept);
        if (!ret) {
            throw new RuntimeException("更新部门失败");
        }
    }

    @Override
    public Dept getDept(Integer id) {
        return deptMapper.getDeptById(id);
    }
    
    /**
     * 检查部门是否有子部门（用于树形菜单显示判断）
     */
    public boolean checkHasSubDept(Integer deptId) {
        int count = deptMapper.hasSubDept(deptId);
        return count > 0;
    }
    
    /**
     * 检查部门是否为4级部门（用于树形菜单显示判断）
     */
    public boolean checkIsFourthLevelDept(Integer deptId) {
        int level = deptMapper.getDeptLevel(deptId);
        return level >= 3; // 0,1,2,3 表示4级
    }
}
