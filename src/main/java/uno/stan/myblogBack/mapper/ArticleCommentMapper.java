package uno.stan.myblogBack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import uno.stan.myblogBack.entity.ArticleComment;

import java.util.List;

@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {
    @Select("select * from article_comment where article_id=#{articleId}")
    List<ArticleComment> selectByArticleId(Long articleId);

    @Delete("delete from article_comment where article_id=#{id}")
    void deleteAllByArticleId(Long id);
}
