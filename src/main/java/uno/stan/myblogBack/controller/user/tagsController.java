package uno.stan.myblogBack.controller.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uno.stan.myblogBack.entity.Tags;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.TagsService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("user/tags")
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
}
