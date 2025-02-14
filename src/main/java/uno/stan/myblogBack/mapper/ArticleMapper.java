package uno.stan.myblogBack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import uno.stan.myblogBack.dto.ArticleConditionDto;
import uno.stan.myblogBack.entity.Article;
import uno.stan.myblogBack.entity.Category;
import uno.stan.myblogBack.vo.ArticleListVo;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    @Update("update article set views = views+1")
    void addViews();

    @Update("update article set likes = likes + 1 where id=#{id}")
    void increaseLike(Long id);

    Page<ArticleListVo> pageQuery(ArticleConditionDto articleConditionDto);


    @Select("select count(*) from article where category_id=#{categoryId}")
    Long checkCategoryIn(Long categoryId);

    @Select("select * from article")
    List<Article> getByTagId(Long tagId);
}
