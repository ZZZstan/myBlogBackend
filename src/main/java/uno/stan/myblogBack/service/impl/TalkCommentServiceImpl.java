package uno.stan.myblogBack.service.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import uno.stan.myblogBack.entity.TalkComment;
import uno.stan.myblogBack.mapper.TalkCommentMapper;
import uno.stan.myblogBack.service.TalkCommentService;
import uno.stan.myblogBack.service.UserService;
import uno.stan.myblogBack.vo.CommentVo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TalkCommentServiceImpl implements TalkCommentService {
    @Resource
    private TalkCommentMapper talkCommentMapper;
    @Resource
    private UserService userService;
    @Override
    public List<CommentVo> getTalkCommentList(Long talkId) {
        List<TalkComment> talkComments=talkCommentMapper.selectByTalkId(talkId);
        List<CommentVo> commentVoList=new ArrayList<>();
        talkComments.forEach(talkComment -> {
            CommentVo commentVo=new CommentVo();
            BeanUtils.copyProperties(talkComment,commentVo);
            commentVo.setUsername(userService.getNameById(commentVo.getUserId()));
            commentVoList.add(commentVo);
        });

        return commentVoList;
    }

    @Override
    public void deleteTalkComment(Long commentId) {
        talkCommentMapper.deleteById(commentId);
    }

    @Override
    public void addTalkComment(TalkComment talkComment) {
        //加上评论时间
        talkComment.setCreatedTime(LocalDateTime.now());

        talkCommentMapper.insert(talkComment);
    }
}
