package uno.stan.myblogBack.service.impl;

import ch.qos.logback.core.util.MD5Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import uno.stan.myblogBack.dto.AdminResetDto;
import uno.stan.myblogBack.dto.UserDto;
import uno.stan.myblogBack.entity.Admin;
import uno.stan.myblogBack.entity.User;
import uno.stan.myblogBack.exception.BaseException;
import uno.stan.myblogBack.mapper.AdminMapper;
import uno.stan.myblogBack.mapper.UserMapper;
import uno.stan.myblogBack.properties.JwtProperties;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.UserService;
import uno.stan.myblogBack.utils.JwtUtil;
import uno.stan.myblogBack.utils.VerificationCodeUtil;
import uno.stan.myblogBack.vo.UserLoginVo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private VerificationCodeUtil verificationCodeUtil;
    @Resource
    private UserMapper userMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private JwtProperties jwtProperties;
    @Override
    public void register(UserDto userDto) {
        if(verificationCodeUtil.Verify(userDto.getEmail(), userDto.getVerificationCode())){
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            user.setRegisterTime(LocalDateTime.now());
            //使用md5加密密码
            String encryptedPassword =DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(encryptedPassword);
            //插入新用户(用户名和邮箱为unique 所以有相同会抛出异常)
            userMapper.insert(user);
            //去除redis的验证码
            verificationCodeUtil.clean(userDto.getEmail());
        }else {
            throw new BaseException("验证码错误!");
        }
    }

    @Override
    public UserLoginVo login(UserDto userDto) {
        String username=userDto.getUsername();
        String password=userDto.getPassword();
        //查找用户名是否存在
        User user=userMapper.getByUsername(username);
        if (user==null){
            throw new BaseException("账户不存在");
        }
        //加密密码并对比
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())){
            throw new BaseException("密码错误");
        }
        //密码屏蔽关键信息
        user.setPassword("******");
        //生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("username",user.getUsername());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        UserLoginVo userLoginVo=new UserLoginVo();
        BeanUtils.copyProperties(user,userLoginVo);
        userLoginVo.setToken(token);

        return userLoginVo;
    }

    @Override
    public boolean check(String token) {
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
        }catch (JwtException e){
            return false;
        }
        return true;
    }

    @Override
    public void resetPassword(UserDto userDto) {
        String email=userDto.getEmail();
        String newPassword=userDto.getPassword();
        newPassword=DigestUtils.md5DigestAsHex(newPassword.getBytes());
        userMapper.updatePasswordByEmail(email,newPassword);
    }

    @Override
    public String getNameById(Long userId) {
        String username=userMapper.getNameById(userId);
        return username;
    }

    @Override
    public List<UserLoginVo> getList() {
        return userMapper.getList();
    }

    @Override
    public void deleteUser(Long userId) {
        userMapper.deleteById(userId);
    }

    @Override
    public UserLoginVo adminLogin(UserDto userDto) {
        String username=userDto.getUsername();
        String password=userDto.getPassword();
        Admin admin=userMapper.getByAdminName(username);
        if (admin==null){
            throw new BaseException("用户名不存在");
        }
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(admin.getPassword())){
            throw new BaseException("密码错误");
        }
        //生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",admin.getId());
        claims.put("username",admin.getUsername());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);
        UserLoginVo userLoginVo=new UserLoginVo();
        BeanUtils.copyProperties(admin,userLoginVo);
        userLoginVo.setToken(token);
        return userLoginVo;
    }

    @Override
    public void resetadminpassword(AdminResetDto adminResetDto) {
        String newPassword= DigestUtils.md5DigestAsHex(adminResetDto.getNewPassword().getBytes());
        adminMapper.changePassword(adminResetDto.getEmail(),newPassword);
    }

    @Override
    public Long getUserNum() {
        return userMapper.selectCount(null);
    }
}
