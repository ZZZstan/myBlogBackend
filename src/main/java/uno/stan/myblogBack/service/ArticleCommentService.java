package uno.stan.myblogBack.service;

import uno.stan.myblogBack.entity.ArticleComment;
import uno.stan.myblogBack.vo.CommentVo;

import java.util.List;

public interface ArticleCommentService {
    List<CommentVo> getArticleCommentById(Long articleId);

    void deleteById(Long commentId);

    void addArticleComment(ArticleComment articleComment);
}
