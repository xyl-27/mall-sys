package com.gec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.dao.BrandMapper;
import com.gec.domain.entity.Brand;
import com.gec.domain.vo.BrandCategoryVO;
import com.gec.domain.vo.BrandVO;
import com.gec.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

//业务类格式编写步骤如下:
//        {1}@Service 注解
//        {2}extends ServiceImpl<M, T> ==> 通用业务类
//        {3}implements 当前业务接口
//        {4}自动装配 ==> 当前实体映射器
//        {5}生成所有接口方法。


@Service
public class BrandServiceImpl
    extends ServiceImpl<BrandMapper, Brand>
    implements IBrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public IPage<Brand> listBrand(Page page, Map data) {
        //1. 创建一个查询器。
        QueryWrapper<Brand> QW = new QueryWrapper<>();
        //2. 动态设置查询条件。
        if( data.get("brandName")!=null ){
            QW.like("brand_name", data.get("brandName"));
        }
        if( data.get("id")!=null ){
            QW.eq("id", data.get("id"));
        }
        //3. 根据查询器条件来检索数据。
        Page retPage = brandMapper.selectPage(page, QW);
        return retPage;
    }

    /*
     * 此方法用于设置品牌与类别间数据关联。
     * 主要操作以下的表: tbl_brand_category.
     */
    @Override
    public void associateCategory(BrandCategoryVO bcVO) {
        //1. 先移除某个品牌的数据关联
        brandMapper.removeAssociate(bcVO.getBrandId());
        //2. 再添加某个品牌的数据关联
        int cnt = brandMapper.associateCategory(bcVO);
        //3. 判断关联成功的类别数是否一样。
        if( cnt!=bcVO.getCategories().size() ){
            throw new RuntimeException("设置品牌数据关联失败。");
        }
    }

    @Override
    public IPage<BrandVO> getListByCategory
            ( Page page, Map data ) {
            return brandMapper.listByCategory(
                    page, data );
    }
    
    @Override
    public void addBrand(Brand brand) {
        // 表单校验
        validateBrandForm(brand);
        
        // 调用父类方法保存
        this.save(brand);
    }
    
    @Override
    public void updateBrand(Brand brand) {
        // 表单校验
        validateBrandForm(brand);
        
        // 调用父类方法更新
        this.updateById(brand);
    }
    
    @Override
    public void deleteBrand(Integer id) {
        // 检查品牌是否有关联数据
        if (hasAssociatedData(id)) {
            throw new RuntimeException("该品牌已关联类别，无法删除");
        }
        
        // 调用父类方法删除
        this.removeById(id);
    }
    
    @Override
    public boolean hasAssociatedData(Integer brandId) {
        Integer count = brandMapper.checkAssociatedData(brandId);
        return count != null && count > 0;
    }
    
    /**
     * 品牌表单校验
     */
    private void validateBrandForm(Brand brand) {
        if (brand == null) {
            throw new RuntimeException("品牌信息不能为空");
        }
        
        // 校验品牌名称
        if (brand.getBrandName() == null || brand.getBrandName().trim().isEmpty()) {
            throw new RuntimeException("品牌名称不能为空");
        }
        
        if (brand.getBrandName().length() > 50) {
            throw new RuntimeException("品牌名称长度不能超过50个字符");
        }
        
        // 校验品牌描述
        if (brand.getBrandDesc() != null && brand.getBrandDesc().length() > 255) {
            throw new RuntimeException("品牌描述长度不能超过255个字符");
        }
        
        // 校验显示状态
        if (brand.getShowStatus() != null && 
            (brand.getShowStatus() != 0 && brand.getShowStatus() != 1)) {
            throw new RuntimeException("显示状态只能是0或1");
        }
        
        // 校验品牌ID（用于更新操作）
        if (brand.getId() != null && brand.getId() <= 0) {
            throw new RuntimeException("品牌ID无效");
        }
    }
}
