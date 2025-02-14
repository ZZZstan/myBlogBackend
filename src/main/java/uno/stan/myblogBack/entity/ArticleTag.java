package uno.stan.myblogBack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTag implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    //文章id
    private Long articleId;

    
    //标签id
    private Long tagId;
}
