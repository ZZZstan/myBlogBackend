package uno.stan.myblogBack.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import uno.stan.myblogBack.entity.BaseInfo;
import uno.stan.myblogBack.entity.Maxim;

import java.util.List;

@Mapper
public interface WebInfoMapper{
    @Select("select notice from web_info")
    String getNotice();

    @Select("select * from maxim")
    List<Maxim> getMaximList();

    @Select("select * from web_info")
    List<BaseInfo> getInfoList();

    @Delete("delete from web_info")
    void deleteBasInfo();

    @Insert("insert into web_info (notice, avatar, signature, homepage_cover, other_cover, qq_cover, wechat_cover) VALUES (#{notice}, #{avatar}, #{signature}, #{homepageCover}, #{otherCover}, #{qqCover}, #{wechatCover})")
    void insertBaseInfo(BaseInfo baseInfo);

    @Delete("delete from maxim")
    void deleteMaxim();


    @Insert("insert into maxim (id,content) values (#{id},#{content})")
    void insertMaxim(Maxim maxim);
}
