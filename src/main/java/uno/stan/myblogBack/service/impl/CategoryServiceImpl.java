package uno.stan.myblogBack.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import uno.stan.myblogBack.entity.Category;
import uno.stan.myblogBack.exception.BaseException;
import uno.stan.myblogBack.mapper.ArticleMapper;
import uno.stan.myblogBack.mapper.CategoryMapper;
import uno.stan.myblogBack.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Override
    public Long getCategoryNum() {
        return categoryMapper.selectCount(null);
    }

    @Override
    public List<Category> getList() {
        List<Category> categoryList = categoryMapper.selectList(null);
        return categoryList;
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateById(category);


    }

    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void delete(Long categoryId) {
        Long num = articleMapper.checkCategoryIn(categoryId);
        if (num>=1){
            throw new BaseException("还有文章关联此分类,无法删除此分类");
        }
        categoryMapper.deleteById(categoryId);
    }
}
