package uno.stan.myblogBack.controller.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uno.stan.myblogBack.entity.Category;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/user/category")
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
}
