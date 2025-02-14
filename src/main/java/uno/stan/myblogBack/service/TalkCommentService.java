package uno.stan.myblogBack.service;

import uno.stan.myblogBack.entity.TalkComment;
import uno.stan.myblogBack.vo.CommentVo;

import java.util.List;

public interface TalkCommentService {
    List<CommentVo> getTalkCommentList(Long talkId);

    void deleteTalkComment(Long commentId);

    void addTalkComment(TalkComment talkComment);
}
