package uno.stan.myblogBack.utils;
import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class VerificationCodeUtil {


    @Resource
    private RedisTemplate redisTemplate;

    //生成验证码并存入redis
    public String generate(String email){
        //生成六位随机数
        String code = RandomUtil.randomNumbers(6);
        //将验证码存入redis，有效期为5分钟
        redisTemplate.opsForValue().set("email_code_"+email, code, 300000, TimeUnit.MILLISECONDS);
        return code;

    }

    //校验验证码
    public boolean Verify(String email, String VerificationCode) {

        String emailKey = "email_code_" + email;

        String storedToken = (String) redisTemplate.opsForValue().get(emailKey);

        if (VerificationCode.equals(storedToken)) {
            return true;
        } else {
            return false;
        }
    }

    //清除redis中的验证码
    public void clean(String email){
        redisTemplate.delete("email_code_" + email);
    }
}
