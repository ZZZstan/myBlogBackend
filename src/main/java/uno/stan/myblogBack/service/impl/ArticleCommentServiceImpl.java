package uno.stan.myblogBack.service.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import uno.stan.myblogBack.entity.ArticleComment;
import uno.stan.myblogBack.mapper.ArticleCommentMapper;
import uno.stan.myblogBack.service.ArticleCommentService;
import uno.stan.myblogBack.service.UserService;
import uno.stan.myblogBack.vo.CommentVo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {
    @Resource
    private ArticleCommentMapper articleCommentMapper;
    @Resource
    private UserService userService;
    @Override
    public List<CommentVo> getArticleCommentById(Long articleId) {
        List<ArticleComment> articleCommentList=articleCommentMapper.selectByArticleId(articleId);
        List<CommentVo> commentVoList =new ArrayList<>();
        articleCommentList.forEach(articleComment -> {
            CommentVo commentVo =new CommentVo();
            String username = userService.getNameById(articleComment.getUserId());
            BeanUtils.copyProperties(articleComment, commentVo);
            commentVo.setUsername(username);
            commentVo.setUserId(articleComment.getUserId());
            commentVoList.add(commentVo);
        });
        return commentVoList;
    }

    @Override
    public void deleteById(Long commentId) {
        articleCommentMapper.deleteById(commentId);
    }

    @Override
    public void addArticleComment(ArticleComment articleComment) {
        articleComment.setCreatedTime(LocalDateTime.now());
        articleCommentMapper.insert(articleComment);
    }
}
