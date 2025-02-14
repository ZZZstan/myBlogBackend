package uno.stan.myblogBack.controller.admin;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uno.stan.myblogBack.entity.Tags;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.TagsService;

import java.util.List;

@RestController("amdinTagController")
@Slf4j
@RequestMapping("/admin/tags")
@CrossOrigin//接受跨域请求
public class tagsController {
    @Resource
    private TagsService tagsService;

    //获得所有Tag
    @GetMapping("/list")
    public Result<List<Tags>> getTagsList(){
        log.info("获取标签信息");
        List<Tags> tagsList = tagsService.getTagsList();
        return Result.success(tagsList);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Tags tag){
        log.info("新增标签:{}",tag);
        tagsService.add(tag);
        return Result.success("添加标签成功");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Tags tag){
        log.info("更新标签:{}",tag);
        tagsService.update(tag);
        return Result.success("更新标签成功");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        log.info("删除标签:{}",id);
        tagsService.delete(id);
        return Result.success("删除标签成功");
    }
}
