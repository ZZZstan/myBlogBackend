package uno.stan.myblogBack.service;

import uno.stan.myblogBack.entity.BaseInfo;
import uno.stan.myblogBack.entity.Maxim;
import uno.stan.myblogBack.vo.BaseInfoVo;
import uno.stan.myblogBack.vo.UserIncreaseVo;

import java.util.List;

public interface WebService {
    Long incrementVisitCount();

    String getNotice();

    List<UserIncreaseVo> getUserIncrease();

    BaseInfo getBaseInfo();

    List<Maxim> getMaxim();

    void updateBaseInfo(BaseInfoVo baseInfoVo);

    void saveChatHistory(String message);

    List<String> getChatHistory();
}
