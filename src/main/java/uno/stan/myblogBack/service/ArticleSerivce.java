package uno.stan.myblogBack.service;

import uno.stan.myblogBack.dto.ArticleConditionDto;
import uno.stan.myblogBack.dto.ArticleDto;
import uno.stan.myblogBack.result.PageResult;
import uno.stan.myblogBack.vo.ArticleListVo;
import uno.stan.myblogBack.vo.ArticleVo;

import java.util.List;

public interface ArticleSerivce {
    List<ArticleListVo> getAllArticle();

    ArticleVo getByid(Long id);

    Long getArticleNum();

    void increaseLike(Long id);

    void updateArticle(ArticleDto articleDto);

    PageResult pageQuery(ArticleConditionDto articleConditionDto);

    void deleteArticle(Long id);

    void addArticle(ArticleDto articleDto);

    List<ArticleListVo> getArticlesByTag(Long tagId);
}
