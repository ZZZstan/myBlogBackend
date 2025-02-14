package uno.stan.myblogBack.controller.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uno.stan.myblogBack.dto.TalkDto;
import uno.stan.myblogBack.entity.ArticleComment;
import uno.stan.myblogBack.entity.Talk;
import uno.stan.myblogBack.entity.TalkComment;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.ArticleCommentService;
import uno.stan.myblogBack.service.TalkCommentService;
import uno.stan.myblogBack.vo.CommentVo;

import java.util.List;

@RestController
@RequestMapping("/user/comment")
@Slf4j
@CrossOrigin//接受跨域请求
public class commentController {
    @Resource
    private ArticleCommentService articleCommentService;
    @Resource
    private TalkCommentService talkCommentService;


    //查询文章评论
    @GetMapping("/article/{articleId}")
    public Result<List<CommentVo>> getArticleCommentList(@PathVariable Long articleId){
        log.info("查询文章相关评论：{}",articleId);
        List<CommentVo> commentVoList =articleCommentService.getArticleCommentById(articleId);
        return Result.success(commentVoList);
    }

    //删除文章评论
    @DeleteMapping("/article/{commentId}")
    public Result deleteArticleComment(@PathVariable Long commentId){
        log.info("删除文章评论:{}",commentId);
        articleCommentService.deleteById(commentId);
        return Result.success();
    }

    //新增文章评论
    @PostMapping("/article/add")
    public Result addArticleComment(@RequestBody ArticleComment articleComment){
        log.info("新增文章评论:{}",articleComment);
        articleCommentService.addArticleComment(articleComment);
        return Result.success();
    }

    //查询说说评论
    @GetMapping("/talk/{talkId}")
    public Result<List<CommentVo>> getTalkCommentList(@PathVariable Long talkId){
        log.info("查询说说评论:{}",talkId);
        List<CommentVo> commentVoList =talkCommentService.getTalkCommentList(talkId);
        return Result.success(commentVoList);
    }

    //新增说说评论
    @PostMapping("/talk")
    public Result addTalkComment(@RequestBody TalkComment talkComment){
        log.info("新增说说评论:{}",talkComment);
        talkCommentService.addTalkComment(talkComment);
        return Result.success();
    }

    //删除说说评论
    @DeleteMapping("/talk/{talkId}")
    public Result deleteTalkComment(@PathVariable Long commentId){
        log.info("删除说说:{}",commentId);
        talkCommentService.deleteTalkComment(commentId);
        return Result.success();
    }
}
