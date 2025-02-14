package uno.stan.myblogBack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import uno.stan.myblogBack.entity.Tags;
import uno.stan.myblogBack.mapper.TagMapper;
import uno.stan.myblogBack.service.TagsService;

import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {
    @Resource
    private TagMapper tagMapper;
    @Override
    public List<Tags> getTagsList() {
        List<Tags> tags = tagMapper.selectList();
        return tags;
    }

    @Override
    public Long getTagsNum() {
        return tagMapper.selectCount(null);
    }

    @Override
    public void add(Tags tag) {
        tagMapper.insert(tag);
    }

    @Override
    public void update(Tags tag) {
        tagMapper.updateById(tag);
    }

    @Override
    public void delete(Long id) {
        tagMapper.deleteById(id);
    }
}
