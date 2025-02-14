package uno.stan.myblogBack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uno.stan.myblogBack.dto.ArticleConditionDto;
import uno.stan.myblogBack.dto.ArticleDto;
import uno.stan.myblogBack.entity.Article;
import uno.stan.myblogBack.entity.ArticleTag;
import uno.stan.myblogBack.entity.Category;
import uno.stan.myblogBack.entity.Tags;
import uno.stan.myblogBack.exception.BaseException;
import uno.stan.myblogBack.mapper.*;
import uno.stan.myblogBack.result.PageResult;
import uno.stan.myblogBack.service.ArticleSerivce;
import uno.stan.myblogBack.vo.ArticleListVo;
import uno.stan.myblogBack.vo.ArticleVo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class ArticleSerivceImpl implements ArticleSerivce {
   @Resource
    private ArticleMapper articleMapper;
   @Resource
   private CategoryMapper categoryMapper;
   @Resource
   private ArticleTagMapper articleTagMapper;
   @Resource
   private TagMapper tagMapper;
   @Resource
   private ArticleCommentMapper articleCommentMapper;

    @Override
    public List<ArticleListVo> getAllArticle() {
        // 创建查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreatedTime); // 根据创建时间升序排序
        //查询获得所有Article对象
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        //新建要返回的voList
        List<ArticleListVo> articleListVos=new ArrayList<>();
        for (Article article : articleList) {
            //新建返回的vo对象
            ArticleListVo vo=new ArticleListVo();
            //根据文章与标签关系表取得该文章的所有标签id
            Long[] ids=articleTagMapper.selectByIdGetTagId(article.getId());
            //根据标签id拿到所有标签名称
            List<Tags> tags=tagMapper.selectListById(ids);
            //将标签名字赋值给vo对象
            vo.setTags(tags);

            //根据分类id返回分类名字
            Category category = categoryMapper.selectById(article.getCategoryId());
            //将分类的名字赋值给vo对象
            vo.setCategory(category);
            //将Article对象的值赋值给vo对象
            BeanUtils.copyProperties(article,vo);
            //添加到集合
            articleListVos.add(vo);
        }

        return articleListVos;
    }

    @Override
    public ArticleVo getByid(Long id) {
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo=new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);

        articleMapper.addViews();

        //根据分类id获得分类名字
        Category category = categoryMapper.selectById(article.getCategoryId());
        articleVo.setCategory(category);

        //根据文章id拿到所有标签id
        Long[] ids=articleTagMapper.selectByIdGetTagId(id);
        //根据标签id拿到所有标签名称
        List<Tags> tags=tagMapper.selectListById(ids);
        //标签名字赋值给vo
        articleVo.setTags(tags);

        System.out.println(articleVo);
        return articleVo;
    }

    //获得文章数量
    @Override
    public Long getArticleNum() {
        return articleMapper.selectCount(null);
    }

    @Override
    public void increaseLike(Long id) {
        articleMapper.increaseLike(id);
    }

    @Override
    public void updateArticle(ArticleDto articleDto) {
        Article article=new Article();
        BeanUtils.copyProperties(articleDto,article);
        article.setUpdatedTime(LocalDateTime.now());
        //先根据文章id批量删除文章标签关联表
        articleTagMapper.deleteByArticleId(article.getId());
        //再根据传来的标签id更新关联表
        articleTagMapper.insert(article.getId(),articleDto.getTagIds());
        log.info("根据文章id和tagId更新关联表,id:{},tagId:{}",article.getId(),articleDto.getTagIds());
        articleMapper.updateById(article);
    }

    @Override
    public PageResult pageQuery(ArticleConditionDto articleConditionDto) {
        PageHelper.startPage(articleConditionDto.getCurrentPage(),articleConditionDto.getPageSize());
        Page<ArticleListVo> page=articleMapper.pageQuery(articleConditionDto);


        page.forEach(articleListVo -> {
            Long id = articleListVo.getId();
            Category category=categoryMapper.getCategoryById(id);
            articleListVo.setCategory(category);
            //根据文章id拿到所有标签id
            Long[] ids=articleTagMapper.selectByIdGetTagId(id);
            //根据标签id拿到所有标签名称
            List<Tags> tags=tagMapper.selectListById(ids);
            articleListVo.setTags(tags);
        });

        log.info("分页查询结果:{}",page);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void deleteArticle(Long id) {
        //删除文章
        articleMapper.deleteById(id);
        //删除关联表内容
        articleTagMapper.deleteByArticleId(id);
        //删除文章评论
        articleCommentMapper.deleteAllByArticleId(id);
    }

    @Override
    @Transactional
    public void addArticle(ArticleDto articleDto) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDto,article);
        article.setCreatedTime(LocalDateTime.now());
        article.setUpdatedTime(LocalDateTime.now());
        articleMapper.insert(article);
        //文章表插入后再根据返回的文章id插入文章标签关联表
        List<Long> tagIds = articleDto.getTagIds();
        tagIds.forEach(tagId->{
            ArticleTag articleTag=new ArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(tagId);
            log.info("关联表新增:{}",articleTag);
            articleTagMapper.addConnection(articleTag);
        });
    }

    @Override
    public List<ArticleListVo> getArticlesByTag(Long tagId) {
        //根据标签id拿到所有文章id
        Long[] articleIds = articleTagMapper.selectByIdGetArticleId(tagId);
        List<ArticleListVo> articleListVos=new ArrayList<>();
        for (int i = 0; i < articleIds.length; i++) {
            Article article = articleMapper.selectById(articleIds[i]);
            ArticleListVo articleListVo = new ArticleListVo();
            BeanUtils.copyProperties(article,articleListVo);
            articleListVos.add(articleListVo);
        }
        return articleListVos;
    }

}
