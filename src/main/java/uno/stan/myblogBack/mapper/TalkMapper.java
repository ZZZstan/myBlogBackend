package uno.stan.myblogBack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import uno.stan.myblogBack.entity.Talk;


import java.util.List;

@Mapper
public interface TalkMapper extends BaseMapper<Talk> {
    @Select("select * from talk")
    List<Talk> getList();

    @Select("select img_url from img_url where talk_id=#{id} order by sequence")
    String[] getUrlById(Long id);

    @Select("select * from talk where status=1 order by created_time desc ")
    Page<Talk> pageQuery();

    @Update("update talk set status = case when status = 1 then 0 when status = 0 then 1 end where id=#{id}")
    void toggleStatus(Long id);

    @Insert("insert into img_url (talk_id, img_url, sequence) values (#{id},#{s},#{i})")
    void insertImg(Long id, String s, int i);

    @Insert("insert into talk (content, created_time, status) VALUES (#{content},#{createdTime},#{status})")
    void insertTalk(Talk talk);

    @Select("select * from talk order by created_time desc ")
    Page<Talk> pageQueryByAdmin();
}
