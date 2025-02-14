package uno.stan.myblogBack.service;

import uno.stan.myblogBack.dto.AdminResetDto;
import uno.stan.myblogBack.dto.UserDto;
import uno.stan.myblogBack.entity.User;
import uno.stan.myblogBack.vo.UserLoginVo;

import java.util.List;

public interface UserService {
    void register(UserDto userDto);

    UserLoginVo login(UserDto userDto);

    boolean check(String token);

    void resetPassword(UserDto userDto);

    String getNameById(Long userId);

    List<UserLoginVo> getList();

    void deleteUser(Long userId);

    UserLoginVo adminLogin(UserDto userDto);

    void resetadminpassword(AdminResetDto adminResetDto);

    Long getUserNum();
}
