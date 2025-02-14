package uno.stan.myblogBack.controller.admin;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uno.stan.myblogBack.dto.ArticleConditionDto;
import uno.stan.myblogBack.dto.ArticleDto;
import uno.stan.myblogBack.result.PageResult;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.ArticleCommentService;
import uno.stan.myblogBack.service.ArticleSerivce;
import uno.stan.myblogBack.vo.ArticleListVo;
import uno.stan.myblogBack.vo.ArticleVo;
import uno.stan.myblogBack.vo.CommentVo;

import java.util.List;


@RestController("adminArticleController")
@RequestMapping("/admin/article")
@Slf4j
@CrossOrigin//接受跨域请求
public class articleController {
    @Resource
    private ArticleSerivce articleSerivce;
    @Resource
    private ArticleCommentService articleCommentService;


    //查询所有文章
    @GetMapping("/list")
    public Result<PageResult> list(ArticleConditionDto articleConditionDto){
        log.info("细节查询文章列表:{}",articleConditionDto);
        PageResult pageResult = articleSerivce.pageQuery(articleConditionDto);
        return Result.success(pageResult);
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

    @PutMapping("/update")
    public Result updateArticle(@RequestBody ArticleDto articleDto){
//        log.info("更新文章:{}",articleDto);
        articleSerivce.updateArticle(articleDto);
        return Result.success("文章更新成功");
    }

    @DeleteMapping("delete/{id}")
    public Result deleteArticle(@PathVariable Long id){
        log.info("根据文章id删除文章:{}",id);
        articleSerivce.deleteArticle(id);
        return Result.success("文章删除成功");
    }

    @PostMapping("/add")
    public Result addArticle(@RequestBody ArticleDto articleDto){
        log.info("新增文章:{}",articleDto);
        articleSerivce.addArticle(articleDto);
        return Result.success("文章添加成功");
    }
    //查询文章评论
    @GetMapping("/comment/{articleId}")
    public Result<List<CommentVo>> getArticleCommentList(@PathVariable Long articleId){
        log.info("管理员查询文章相关评论：{}",articleId);
        List<CommentVo> commentVoList =articleCommentService.getArticleCommentById(articleId);
        return Result.success(commentVoList);
    }
    //管理员删除评论
    @DeleteMapping("comment/delete/{commentId}")
    public Result deleteArticleComment(@PathVariable Long commentId){
        log.info("管理员删除文章评论:{}",commentId);
        articleCommentService.deleteById(commentId);
        return Result.success();
    }

}
