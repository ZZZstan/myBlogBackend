package uno.stan.myblogBack.controller.common;

import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uno.stan.myblogBack.result.Result;

import uno.stan.myblogBack.utils.AliOssUtil;
import uno.stan.myblogBack.utils.VerificationCodeUtil;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequestMapping("/common")
@Slf4j
@RestController
@CrossOrigin
public class commonController {
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private AliOssUtil aliOssUtil;

    @Resource
    private VerificationCodeUtil verificationCodeUtil;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.nickname}")
    private String nickname;

    //文件上传
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file){
        log.info("文件上传:{}",file);

        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //文件后缀
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID命名防止重名
        String newfilename = UUID.randomUUID().toString() + fileType;
        try {
            String path = aliOssUtil.upload(file.getBytes(), newfilename);

            return Result.success(path);
        } catch (IOException e) {
            log.error("文件上传失败:{}",e);
        }
        return Result.error("文件上传失败");
    }

    //邮箱验证码
    @GetMapping("/code")
    public Result email(@RequestParam("email") String email){
        log.info("邮箱验证码:{}",email);
        // 创建一个邮件
        SimpleMailMessage message = new SimpleMailMessage();

        // 设置发件人
        message.setFrom(nickname+'<'+sender+'>');

        // 设置收件人
        message.setTo(email);

        // 设置邮件主题
        message.setSubject("欢迎访问"+nickname+"的个人博客");

        String code=verificationCodeUtil.generate(email);

        String content = "【验证码】您的验证码为：" + code + " 。 验证码五分钟内有效，逾期作废。\n\n\n" +
                "------------------------------\n\n\n" ;

        message.setText(content);

        // 发送邮件
        LocalDateTime begin= LocalDateTime.now();

        javaMailSender.send(message);

        LocalDateTime end=LocalDateTime.now();
        Long time= ChronoUnit.SECONDS.between(begin,end);
        log.info("send方法耗时:{}秒",time);
        System.out.println("after sending");

        return Result.success("验证码发送成功,请进入邮箱查收");
    }
}
