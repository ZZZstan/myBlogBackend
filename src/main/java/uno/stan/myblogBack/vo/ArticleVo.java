package uno.stan.myblogBack.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uno.stan.myblogBack.entity.Category;
import uno.stan.myblogBack.entity.Tags;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo implements Serializable {

    //文章id
    private Long id;

    //文章名称
    private String title;

    //文章描述
    private String description;

    //文章内容
    private String content;

    //文章分类
    private Category category;

    //文章标签
    private List<Tags> tags;

    //封面地址
    private  String imgUrl;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

    //点赞数
    private Long likes;

    //浏览数
    private Long views;
}
