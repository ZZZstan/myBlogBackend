package uno.stan.myblogBack.controller.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uno.stan.myblogBack.result.PageResult;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.TalkService;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/user/talk")
public class talkController {
    @Resource
    private TalkService talkService;

    @GetMapping
    public Result<PageResult> getTalkPageList( int currentPage, int pageSize){
        log.info("查询所有说说:{},{}",currentPage,pageSize);
        PageResult pageResult=talkService.getPage(currentPage,pageSize);
        System.out.println(pageResult);
        return Result.success(pageResult);
    }
}
