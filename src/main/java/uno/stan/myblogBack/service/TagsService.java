package uno.stan.myblogBack.service;

import uno.stan.myblogBack.entity.Tags;

import java.util.List;

public interface TagsService {
    List<Tags> getTagsList();

    Long getTagsNum();

    void add(Tags tag);

    void update(Tags tag);

    void delete(Long id);
}
