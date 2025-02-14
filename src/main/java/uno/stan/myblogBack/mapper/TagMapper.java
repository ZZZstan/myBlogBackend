package uno.stan.myblogBack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import uno.stan.myblogBack.entity.Tags;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tags> {


    //根据多个标签id获得多个标签名字
    String[] selectByIdGetName(Long[] ids);

    //获得所有标签名称
    @Select("select * from tags")
    List<Tags> selectList();

    List<Tags> selectListById(Long[] ids);
}
