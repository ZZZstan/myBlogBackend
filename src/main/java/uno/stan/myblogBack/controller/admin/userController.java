package uno.stan.myblogBack.controller.admin;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uno.stan.myblogBack.dto.AdminResetDto;
import uno.stan.myblogBack.dto.UserDto;
import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.UserService;
import uno.stan.myblogBack.utils.VerificationCodeUtil;
import uno.stan.myblogBack.vo.UserLoginVo;

import java.util.List;

@RestController("adminUserController")
@Slf4j
@RequestMapping("/admin/user")
@CrossOrigin
public class userController {
    @Resource
    private UserService userService;
    @Resource
    private VerificationCodeUtil verificationCodeUtil;

    @GetMapping("/list")
    public Result<List<UserLoginVo>> getUserList(){
        log.info("获取所有用户列表");
        List<UserLoginVo> userLoginVoList=userService.getList();
        return Result.success(userLoginVoList);
    }

    @DeleteMapping("/delete/{userId}")
    public Result deleteUser(@PathVariable Long userId){
        log.info("删除用户:{}",userId);
        userService.deleteUser(userId);
        return Result.success("删除用户成功");
    }

    @PostMapping("/login")
    public Result<UserLoginVo> adminLogin(@RequestBody UserDto userDto){
        log.info("管理员登录:{}",userDto);
        UserLoginVo userLoginVo=userService.adminLogin(userDto);
        return Result.success(userLoginVo);
    }

    @PostMapping("/resetadminpassword")
    public Result resetAdminPassword(@RequestBody AdminResetDto adminResetDto){
        log.info("重置管理员密码:{}",adminResetDto);
        log.info("验证验证码:{}",adminResetDto.getCode());
        if (verificationCodeUtil.Verify(adminResetDto.getEmail(),adminResetDto.getCode())){
            verificationCodeUtil.clean(adminResetDto.getEmail());
            userService.resetadminpassword(adminResetDto);
            return Result.success("修改密码成功!");
        }
        return Result.error("验证码校验失败");
    }
}
