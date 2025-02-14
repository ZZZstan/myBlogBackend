package uno.stan.myblogBack.controller.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uno.stan.myblogBack.dto.ArticleConditionDto;
import uno.stan.myblogBack.result.PageResult;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.ArticleCommentService;
import uno.stan.myblogBack.service.ArticleSerivce;
import uno.stan.myblogBack.vo.ArticleListVo;
import uno.stan.myblogBack.vo.ArticleVo;

import java.util.List;


@RestController
@RequestMapping("/user/article")
@Slf4j
@CrossOrigin//接受跨域请求
public class articleController {
    @Resource
    private ArticleSerivce articleSerivce;



    //查询所有文章
    @GetMapping("/list")
    public Result<List<ArticleListVo>> list(){
        log.info("查询文章列表");
        List<ArticleListVo> allArticle = articleSerivce.getAllArticle();
        return Result.success(allArticle);
    }

    //根据id获取文章详情
    @GetMapping
    public Result<ArticleVo> getById(@RequestParam Long id){
        log.info("根据文章id查询文章,{}",id);
        if (id == null || id <= 0) {
            return Result.error("无效的文章ID");
        }
        ArticleVo articleVo= articleSerivce.getByid(id);

        return Result.success(articleVo);
    }

    //点赞文章
    @GetMapping("/like/{id}")
    public Result increaseLike(@PathVariable Long id){
        log.info("增加文章的likes:{}",id);
        articleSerivce.increaseLike(id);
        return Result.success();
    }


    //查询所有文章
    @GetMapping("/condition/list")
    public Result<PageResult> list(ArticleConditionDto articleConditionDto){
        log.info("用户细节查询文章列表:{}",articleConditionDto);
        PageResult pageResult = articleSerivce.pageQuery(articleConditionDto);
        return Result.success(pageResult);
    }
}
