package uno.stan.myblogBack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uno.stan.myblogBack.result.PageResult;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ArticleDto implements Serializable {
    //文章id
    private Long id;
    //文章标题
    private String title;
    //文章描述
    private String description;
    //文章内容
    private String content;
    //文章分类id
    private Long categoryId;
    //文章标签id
    private List<Long> tagIds;
    //封面图片地址
    private String imgUrl;
}
