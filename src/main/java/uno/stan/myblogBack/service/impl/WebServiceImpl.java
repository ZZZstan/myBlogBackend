package uno.stan.myblogBack.service.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uno.stan.myblogBack.entity.BaseInfo;
import uno.stan.myblogBack.entity.Maxim;
import uno.stan.myblogBack.mapper.UserMapper;
import uno.stan.myblogBack.mapper.WebInfoMapper;
import uno.stan.myblogBack.service.WebService;
import org.springframework.data.redis.core.RedisTemplate;
import uno.stan.myblogBack.vo.BaseInfoVo;
import uno.stan.myblogBack.vo.UserIncreaseVo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WebServiceImpl implements WebService {
    private static final String VISIT_COUNT_KEY = "visit_count";
    private static final String CHAT_HISTORY_KEY = "chat_room:messages";

    @Resource
    private UserMapper userMapper;
    @Resource
    private WebInfoMapper webInfoMapper;


//    @Autowired(required = false)
//    public void setRedisTemplate(RedisTemplate redisTemplate) {
//        RedisSerializer stringSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(stringSerializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setHashValueSerializer(stringSerializer);
//        this.redisTemplate = redisTemplate;
//    }
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public Long incrementVisitCount() {
        return redisTemplate.opsForValue().increment(VISIT_COUNT_KEY);
    }

    @Override
    public String getNotice() {
        return webInfoMapper.getNotice();
    }


    @Override
    public List<UserIncreaseVo> getUserIncrease() {
        //返回结果的列表
        List<UserIncreaseVo> userIncreaseVoList=new ArrayList<>();
        //今天的年月日
        LocalDate today=LocalDate.now();
        for (int i = 7; i > 0 ; i--) {
            //一天的开始 xxxx年xx月xx日T00:00:00
            LocalDateTime start =today.atStartOfDay();
            //一天的结束 xxxx年xx月xx日T23:59:59
            LocalDateTime end =today.atTime(23,59,59);
            //mapper查询返回增长数
            Long increaseNum = userMapper.getIncreaseNum(start,end);
            //增长数和日期一起存进去
            UserIncreaseVo build = UserIncreaseVo.builder().increaseNumber(increaseNum).registerTime(today).build();
            userIncreaseVoList.add(build);
            //去前一天找增长数
            today=today.minusDays(1);
        }
        return userIncreaseVoList;
    }

    @Override
    public BaseInfo getBaseInfo() {
        List<BaseInfo> baseInfos = webInfoMapper.getInfoList();
        return baseInfos.get(0);
    }

    @Override
    public List<Maxim> getMaxim() {
        return webInfoMapper.getMaximList();
    }

    @Override
    @Transactional
    public void updateBaseInfo(BaseInfoVo baseInfoVo) {
        BaseInfo baseInfo=new BaseInfo();
        BeanUtils.copyProperties(baseInfoVo,baseInfo);
        List<Maxim> maximList=baseInfoVo.getMaximList();
        //先删除基本信息再插入完成更新
        webInfoMapper.deleteBasInfo();
        webInfoMapper.insertBaseInfo(baseInfo);
        //先删除格言再插入
        webInfoMapper.deleteMaxim();
        maximList.forEach(maxim -> {
            webInfoMapper.insertMaxim(maxim);
        });
    }

    @Override
    public void saveChatHistory(String message) {
        // 将新消息添加到列表的左侧
        redisTemplate.opsForList().leftPush(CHAT_HISTORY_KEY,message);
        // 保持列表最多只有 50 条记录
        redisTemplate.opsForList().trim(CHAT_HISTORY_KEY, 0, 49);
    }

    @Override
    public List<String> getChatHistory() {
        List<String> chatHistory = redisTemplate.opsForList().range(CHAT_HISTORY_KEY, 0, 49);
        if (chatHistory != null) {
            Collections.reverse(chatHistory); // 反转列表
        }
        return chatHistory;
    }

}
