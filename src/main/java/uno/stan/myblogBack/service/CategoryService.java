package uno.stan.myblogBack.service;

import uno.stan.myblogBack.entity.Category;

import java.util.List;

public interface CategoryService {
    Long getCategoryNum();

    List<Category> getList();

    void update(Category category);

    void add(Category category);

    void delete(Long categoryId);
}
