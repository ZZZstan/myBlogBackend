package uno.stan.myblogBack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/*@TableName("article")*/
public class Article implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)//声明是自增的,不然会用雪花算法随机一个id
    //文章id
    private Long id;

    //文章标题
    private String title;

    //文章简介
    private String description;

    //文章内容
    private String content;

    //文章分类id
    private Long categoryId;

    //封面地址
    private String imgUrl;

    //发布时间
    private LocalDateTime createdTime;

    //更新时间
    private LocalDateTime updatedTime;

    //点赞数
    private Long likes;

    //浏览数
    private Long views;

}
