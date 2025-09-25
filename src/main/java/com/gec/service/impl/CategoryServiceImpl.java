package com.gec.service.impl;

/*
* 业务类格式编写步骤如下:
* {1}@Service 注解
* {2}extends ServiceImpl<M, T> ==> 通用业务类实现
* {3}implements 当前业务接口 \ IBaseService
* {4}自动装配 ==> 当前实体映射器
* {5}生成所有接口方法。
*/

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.dao.CategoryMapper;
import com.gec.domain.bo.CategoryBO;
import com.gec.domain.entity.Brand;
import com.gec.domain.entity.Category;
import com.gec.domain.entity.Node;
import com.gec.domain.vo.BrandVO;
import com.gec.domain.vo.CategoryBrandVO;
import com.gec.service.IBaseService;
import com.gec.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl
        extends ServiceImpl<CategoryMapper, Category>
        implements ICategoryService, IBaseService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Node> listCategory() {
        //1.创建条件查询器
        QueryWrapper<Category> WR = new QueryWrapper<>();
        //2.调用通过方法来执行查询。
        List<Category> list = categoryMapper.selectList(WR);
        //3.将线性结构数据 ==> 层次型结构数据。
        List<Node> nodes = convertNodeBO(list);
        //4.返回顶层的类别列表。
        return nodes;
    }

    /*
     * 此方法：用于实现实体扩展类的属性拷贝。
     * 在这里，可能涉及一些特殊的需求的实现。
     * (或一些特殊的数据逻辑处理。)
     */
    @Override
    public Node copyObj(Node node) {
        Category category = (Category) node;
        // 创建一个CategoryBO对象
        CategoryBO categoryBO = new CategoryBO();
        // 正确复制Category对象的属性到CategoryBO对象
        categoryBO.setId(category.getId());
        categoryBO.setCategoryName(category.getCategoryName());
        categoryBO.setParentId(category.getParentId());
        categoryBO.setpIds(category.getPIds());
        categoryBO.setShowStatus(category.getShowStatus());
        categoryBO.setSort(category.getSort());
        categoryBO.setLevel(category.getLevel());
        return categoryBO;
    }

    //实现三级联动菜单
    /*
     * 由于 vue 前端需要我们提供一个这样的数组
     * （用于三级联动菜单）
     * 如: [1, 10, 12]
     * 如: 得到 "新类别名称12" 的 id 序列
     * ID: 12
     * 先得到 PIDS: [0, 1, 10]
     * 再把 0 去掉，把 12 填上
     * 最终得到数组: [1 ,10, 12]
     */
    @Override
    public Integer[] getPidsArr(Integer id) {
        String ids = categoryMapper.getPids(id);
        //1.这里会得到 "1, 10, 12"
        ids = ids.replace("0,", "") + "," + id;
        //2.把以上字符序列拆解出来 ==> 数组
        String[] arr = ids.split(",");
        //3.定义一个等大数组
        Integer[] i_arr = new Integer[ arr.length ];
        for(int i=0; i<arr.length; i++){
            i_arr[ i ] = Integer.valueOf( arr[i] );
        }

        return i_arr;
    }

    @Override
    public String getPids(Integer id) {
        return categoryMapper.getPids( id );
    }

    /*
     * 这个方法用于建立 "类别与品牌" 关联数据。
     * 游戏手机 ==> 华为
     * （类别）     中兴
     *            小米
     *            OPPO
     */
    @Override
    public void associateBrand(CategoryBrandVO cbVO) {
        //1.先将原先关联移除。
        Integer categoryId = cbVO.getCategoryId();
        List<Integer> brandIds = cbVO.getDeleteIds();
        categoryMapper.removeAssociate(categoryId,brandIds);
        //2.再建立新的关联关系。
        int cnt = categoryMapper.associateBrand(cbVO);
        //3.判定更新数是否等于 brand 条目数。
        if (cnt != cbVO.getBrands().size()) {
            throw new RuntimeException("设置类别与品牌关联失败。");
        }
    }
    
    @Override
    public boolean canDeleteCategory(Integer categoryId) {
        // 检查是否有子类别
        int subCategoryCount = categoryMapper.hasSubCategories(categoryId);
        if (subCategoryCount > 0) {
            return false;
        }
        
        // 检查是否有关联的品牌
        int brandCount = categoryMapper.hasAssociatedBrands(categoryId);
        if (brandCount > 0) {
            return false;
        }
        
        // 暂时跳过商品检查，因为商品上架功能尚未实现，tbl_goods表不存在
        // 后续实现商品上架功能时，再取消注释以下代码
        /*
        // 检查是否有商品
        int productCount = categoryMapper.hasProducts(categoryId);
        if (productCount > 0) {
            return false;
        }
        */
        
        return true;
    }
    
    @Override
    public void deleteCategory(Integer categoryId) {
        // 执行删除操作
        int count = categoryMapper.deleteById(categoryId);
        if (count <= 0) {
            throw new RuntimeException("删除类别失败");
        }
    }

}
