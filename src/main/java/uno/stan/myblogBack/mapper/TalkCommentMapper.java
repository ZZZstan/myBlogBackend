package uno.stan.myblogBack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import uno.stan.myblogBack.entity.TalkComment;

import java.util.List;

@Mapper
public interface TalkCommentMapper extends BaseMapper<TalkComment> {
    @Select("select * from talk_comment where talk_id=#{talkId}")
    List<TalkComment> selectByTalkId(Long talkId);

    @Delete("delete from talk_comment where talk_id=#{id}")
    void deleteAllByTalkId(Long id);
}
