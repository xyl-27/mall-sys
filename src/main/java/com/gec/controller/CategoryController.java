package com.gec.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gec.components.FileTemplate;
import com.gec.domain.entity.Category;
import com.gec.domain.entity.Node;
import com.gec.domain.vo.CategoryBrandVO;
import com.gec.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Category")
public class CategoryController extends BrandController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/list")
    public R list(){
        List<Node> nodes = null;
        nodes = categoryService.listCategory();
        return R.ok(nodes);
    }

    @PostMapping("/addCategory")
    public R addCategory(@RequestBody Category category){
        setPids(category);
        boolean ret = categoryService.save(category);
        if(!ret){
            throw new RuntimeException("保存类别失败");
        }
        return R.ok();
    }

    private void setPids(Category category){
        Integer parID = category.getParentId();
        String pids = categoryService.getPids(parID);
        category.setPIds(pids+","+parID);
    }

    @PostMapping("/updateCategory")
    public R updateCategory(@RequestBody Category category){
        setPids(category);
        UpdateWrapper<Category> UW = new UpdateWrapper<>();
        UW.eq("id", category.getId());
        boolean ret = categoryService.update(category, UW);
        if(!ret){
            throw new RuntimeException("更新类别失败");
        }
        return R.ok();
    }

    @PostMapping(value = "/associateBrand")
    public R associateBrand(@RequestBody CategoryBrandVO cbVO){
        categoryService.associateBrand(cbVO);
        return R.ok();
    }

    @GetMapping(value = "/getPids/{id}")
    public R getPids(@PathVariable("id")Integer id){
        Integer[] ids = {};
        ids = categoryService.getPidsArr(id);
        System.out.println(ids);
        return R.ok(ids);
    }

    @DeleteMapping("/delete/{id}")
    public R delectCategory(@PathVariable("id")Integer id) {
        try {
            Boolean isDeleted = categoryService.removeCategoryById(id);
            if(isDeleted){
                return R.ok("类别删除成功");
            }else{
                Exception ex = new RuntimeException("删除失败");
                return R.err(ex);
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.err(e);
        }
    }

    @Override
    protected FileTemplate getFileTemplate() {
        return null;
    }
}
