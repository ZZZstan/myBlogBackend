package uno.stan.myblogBack.controller.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uno.stan.myblogBack.dto.UserDto;

import uno.stan.myblogBack.result.Result;
import uno.stan.myblogBack.service.UserService;
import uno.stan.myblogBack.utils.VerificationCodeUtil;
import uno.stan.myblogBack.vo.UserLoginVo;

@RestController
@Slf4j
@RequestMapping("/user")
@CrossOrigin
public class userController {
    @Resource
    private UserService userService;
    @Resource
    private VerificationCodeUtil verificationCodeUtil;

    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto){
        log.info("用户注册:{}",userDto);
        userService.register(userDto);
        return Result.success("注册成功!");
    }

    @PostMapping("/login")
    public Result<UserLoginVo> login(@RequestBody UserDto userDto){
        log.info("用户登录:{}",userDto);
        UserLoginVo userLoginVo = userService.login(userDto);
        return Result.success(userLoginVo);
    }

    @GetMapping("/check")
    public Result check(@RequestHeader String token){
        log.info("校验jwt:{}",token);
        if (!userService.check(token)){
            return Result.error("令牌校验失败");
        }
        return Result.success("令牌校验成功");
    }

    @PostMapping("/verifyCode")
    public Result verifyCode(@RequestBody UserDto userDto){
        log.info("验证验证码:{}",userDto);
        if (verificationCodeUtil.Verify(userDto.getEmail(),userDto.getVerificationCode())){
            verificationCodeUtil.clean(userDto.getEmail());
            return Result.success();
        }
        return Result.error("校验失败");
    }

    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestBody UserDto userDto){
        log.info("重置密码：{}",userDto);
        userService.resetPassword(userDto);
        return Result.success();
    }
}
