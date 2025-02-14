package uno.stan.myblogBack.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uno.stan.myblogBack.dto.TalkDto;
import uno.stan.myblogBack.entity.Talk;
import uno.stan.myblogBack.mapper.TalkCommentMapper;
import uno.stan.myblogBack.mapper.TalkMapper;
import uno.stan.myblogBack.result.PageResult;
import uno.stan.myblogBack.service.TalkCommentService;
import uno.stan.myblogBack.service.TalkService;
import uno.stan.myblogBack.vo.TalkVo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TalkServiceImpl implements TalkService {
    @Resource
    private TalkMapper talkMapper;
    @Resource
    private TalkCommentMapper talkCommentMapper;
    @Override
    public List<TalkVo> getList() {
        List<Talk> talkList=talkMapper.getList();
        List<TalkVo> talkVoList=new ArrayList<>();
        talkList.forEach(talk -> {
            String[] url=talkMapper.getUrlById(talk.getId());
            TalkVo talkVo=new TalkVo();
            BeanUtils.copyProperties(talk,talkVo);
            talkVo.setImgUrl(url);
            talkVoList.add(talkVo);
        });
        return talkVoList;
    }

    @Override
    public PageResult getPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Talk> page =talkMapper.pageQuery();
        List<TalkVo> talkVoList=new ArrayList<>();
        page.forEach(l->{
            String[] urls=talkMapper.getUrlById(l.getId());
            TalkVo build = TalkVo.builder().id(l.getId()).createdTime(l.getCreatedTime()).imgUrl(urls).content(l.getContent()).status(l.getStatus()).build();
            talkVoList.add(build);
        });

        return new PageResult(page.getTotal(),talkVoList);
    }

    @Override
    public void toggleStatus(Long id) {
        talkMapper.toggleStatus(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        talkMapper.deleteById(id);
        //删除该说说下的所有评论
        talkCommentMapper.deleteAllByTalkId(id);
    }

    @Override
    public void addTalk(TalkDto talkDto) {
        Talk talk = Talk.builder().content(talkDto.getContent()).createdTime(LocalDateTime.now()).status(1L).build();
        talkMapper.insert(talk);
        if (talkDto.getImgUrl()!=null) {
            String[] imgUrl = talkDto.getImgUrl();
            for (int i = 0; i < imgUrl.length; i++) {
                talkMapper.insertImg(talk.getId(),imgUrl[i],i);
            }
        }
    }

    @Override
    public PageResult getPageByAdmin(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Talk> page =talkMapper.pageQueryByAdmin();
        List<TalkVo> talkVoList=new ArrayList<>();
        page.forEach(l->{
            String[] urls=talkMapper.getUrlById(l.getId());
            TalkVo build = TalkVo.builder().id(l.getId()).createdTime(l.getCreatedTime()).imgUrl(urls).content(l.getContent()).status(l.getStatus()).build();
            talkVoList.add(build);
        });
        return new PageResult(page.getTotal(),talkVoList);
    }
}
