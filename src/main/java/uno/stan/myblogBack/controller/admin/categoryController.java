package uno.stan.myblogBack.controller.admin;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uno.stan.myblogBack.entity.Category;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.CategoryService;

import java.util.List;

@RestController("adminCategoryController")
@RequestMapping("/admin/category")
@Slf4j
@CrossOrigin//接受跨域请求
public class categoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<Category>> getList(){
        log.info("获得所有分类");
        List<Category> categoryList=categoryService.getList();
        return Result.success(categoryList);
    }

    @PutMapping("/update")
    public Result updateCategory(@RequestBody Category category){
        log.info("更新分类:{}",category);
        categoryService.update(category);
        return Result.success("更新分类成功");
    }

    @PostMapping("/add")
    public Result addCategory(@RequestBody Category category){
        log.info("新增分类:{}",category);
        categoryService.add(category);
        return Result.success("新增分类成功");
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteCategory(@PathVariable Long id){
        log.info("根据id删除分类:{}",id);
        categoryService.delete(id);
        return Result.success("删除分类成功");
    }
}
