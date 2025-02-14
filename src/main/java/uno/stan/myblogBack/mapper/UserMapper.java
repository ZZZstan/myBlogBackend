package uno.stan.myblogBack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import uno.stan.myblogBack.entity.Admin;
import uno.stan.myblogBack.entity.User;
import uno.stan.myblogBack.vo.UserLoginVo;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username=#{username}")
    User getByUsername(String username);

    @Update("update user set password=#{newPassword} where email=#{email}")
    void updatePasswordByEmail(String email, String newPassword);

    @Select("select username from user where id=#{userId}")
    String getNameById(Long userId);

    @Select("select id,username,email,register_time from user")
    List<UserLoginVo> getList();

    @Select("select * from admin where username=#{username}")
    Admin getByAdminName(String username);

    @Select("select count(*) from user where register_time>#{start} and register_time<#{end}")
    Long getIncreaseNum(LocalDateTime start, LocalDateTime end);
}
