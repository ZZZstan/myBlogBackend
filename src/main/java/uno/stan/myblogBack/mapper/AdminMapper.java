package uno.stan.myblogBack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import uno.stan.myblogBack.entity.Admin;

import java.util.Map;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    @Update("update admin set password=#{newPassword} where email=#{email}")
    void changePassword(String email, String newPassword);
}
