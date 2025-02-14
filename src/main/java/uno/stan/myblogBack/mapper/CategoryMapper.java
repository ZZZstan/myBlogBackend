package uno.stan.myblogBack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import uno.stan.myblogBack.entity.Category;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    @Select("select category_name from category where id=#{categoryId}")
    String selectByIdGetName(Long categoryId);

    //根据文章id获得文章分类对象
    @Select("select * from category where id =(select category_id from article where article.id=#{id})")
    Category getCategoryById(Long id);
}
