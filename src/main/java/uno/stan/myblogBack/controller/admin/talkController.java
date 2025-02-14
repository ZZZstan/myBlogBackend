package uno.stan.myblogBack.controller.admin;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uno.stan.myblogBack.dto.TalkDto;
import uno.stan.myblogBack.entity.Talk;
import uno.stan.myblogBack.result.PageResult;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.ArticleCommentService;
import uno.stan.myblogBack.service.TalkCommentService;
import uno.stan.myblogBack.service.TalkService;
import uno.stan.myblogBack.vo.CommentVo;

import java.util.List;

@RestController("adminTalkController")
@Slf4j
@CrossOrigin
@RequestMapping("/admin/talk")
public class talkController {
    @Resource
    private TalkService talkService;
    @Resource
    private TalkCommentService talkCommentService;

    @GetMapping("/list")
    public Result<PageResult> getTalkPageList( int currentPage, int pageSize){
        log.info("管理员查询所有说说:{},{}",currentPage,pageSize);
        PageResult pageResult=talkService.getPageByAdmin(currentPage,pageSize);
        System.out.println(pageResult);
        return Result.success(pageResult);
    }

    @PutMapping("/toggle/{id}")
    public Result toggleTalkStatus(@PathVariable Long id){
        talkService.toggleStatus(id);
        return Result.success("更改状态成功");
    }

    @DeleteMapping("delete/{id}")
    public Result deleteById(@PathVariable Long id){
        log.info("删除说说:{}",id);
        talkService.deleteById(id);
        return Result.success("删除说说成功");
    }

    @Transactional
    @PostMapping("/add")
    public Result addTalk(@RequestBody TalkDto talkDto){
        log.info("新增说说:{}",talkDto);
        talkService.addTalk(talkDto);
        return Result.success("新增说说成功");
    }
    //管理员获得说说评论列表
    @GetMapping("/comment/{talkId}")
    public Result<List<CommentVo>> getTalkCommentList(@PathVariable Long talkId){
        log.info("查询说说评论:{}",talkId);
        List<CommentVo> commentVoList =talkCommentService.getTalkCommentList(talkId);
        return Result.success(commentVoList);
    }
    //管理员删除说说评论
    @DeleteMapping("/comment/delete/{commentId}")
    public Result deleteTalkComment(@PathVariable Long commentId){
        log.info("管理员删除说说:{}",commentId);
        talkCommentService.deleteTalkComment(commentId);
        return Result.success();
    }

}
