package uno.stan.myblogBack.controller.common;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import uno.stan.myblogBack.entity.BaseInfo;
import uno.stan.myblogBack.entity.Maxim;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.*;
import uno.stan.myblogBack.vo.BaseInfoVo;
import uno.stan.myblogBack.vo.UserIncreaseVo;
import uno.stan.myblogBack.vo.WebInfoVo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static uno.stan.myblogBack.vo.WebInfoVo.WEB_CREATED_TIME;

@RestController
@RequestMapping("/webinfo")
@Slf4j
@CrossOrigin
public class webInfoController {
    @Resource
    private ArticleSerivce articleSerivce;
    @Resource
    private TagsService tagsService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private WebService webService;
    @Resource
    private UserService userService;

    @GetMapping
    public Result<WebInfoVo> getInfo(){
        log.info("查询网站信息");
        //获取运行时间
        Long runTime= ChronoUnit.DAYS.between(WEB_CREATED_TIME,LocalDate.now());
        //获得文章数量
        Long articleNum=articleSerivce.getArticleNum();
        //获得标签数量
        Long tagsNum=tagsService.getTagsNum();
        //获得分类数目
        Long categoryNum=categoryService.getCategoryNum();
        //获取访问次数
        Long visits=webService.incrementVisitCount();
        //获取用户人数
        Long UserNum =userService.getUserNum();

        WebInfoVo webInfoVo=WebInfoVo.builder().runTime(runTime).articleNum(articleNum).tagNum(tagsNum).categoryNum(categoryNum).visits(visits).userNum(UserNum).build();
        return Result.success(webInfoVo);
    }
    @GetMapping("/baseinfo")
    public Result<BaseInfoVo> getBaseInfo(){
        log.info("查询网页基本信息");
        BaseInfo baseInfo =webService.getBaseInfo();
        List<Maxim> maximList=webService.getMaxim();
        BaseInfoVo baseInfoVo= new BaseInfoVo();
        BeanUtils.copyProperties(baseInfo,baseInfoVo);
        baseInfoVo.setMaximList(maximList);

        return Result.success(baseInfoVo);
    }
    @PutMapping("baseinfo")
    public Result updateBaseInfo(@RequestBody BaseInfoVo baseInfoVo){
        log.info("更新网站基本信息：{}",baseInfoVo);
        webService.updateBaseInfo(baseInfoVo);
        return Result.success();
    }

    //七日内用户增长数
    @GetMapping("/userincrease")
    public Result<List<UserIncreaseVo>>  userIncrease(){
        log.info("查询用户增长数");
        List<UserIncreaseVo> userIncreaseVoList=webService.getUserIncrease();
        return Result.success(userIncreaseVoList);
    }
}
