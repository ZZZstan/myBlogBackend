package uno.stan.myblogBack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import uno.stan.myblogBack.entity.ArticleTag;

import java.util.List;

@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    @Select("select tag_id from article_tag where article_id=#{articleId}")
    Long[] selectByIdGetTagId(Long articleId);

    //根据文章id批量删除文章标签关联表
    @Delete("delete from article_tag where article_id=#{articleId}")
    void deleteByArticleId(Long articleId);

    void insert(Long id, List<Long> tagIds);

    @Insert("insert into article_tag (article_id,tag_id) values (#{articleId},#{tagId})")
    void addConnection(ArticleTag articleTag);

    @Select("select article_id from article_tag where tag_id=#{tagId}")
    Long[] selectByIdGetArticleId(Long tagId);
}
