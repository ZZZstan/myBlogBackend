package uno.stan.myblogBack.service;

import uno.stan.myblogBack.dto.TalkDto;
import uno.stan.myblogBack.result.PageResult;
import uno.stan.myblogBack.vo.TalkVo;

import java.util.List;

public interface TalkService {

    List<TalkVo> getList();

    PageResult getPage(int currentPage, int pageSize);

    void toggleStatus(Long id);

    void deleteById(Long id);

    void addTalk(TalkDto talkDto);

    PageResult getPageByAdmin(int currentPage, int pageSize);
}
