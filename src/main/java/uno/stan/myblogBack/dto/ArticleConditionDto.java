package uno.stan.myblogBack.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ArticleConditionDto implements Serializable {
    //文章标题
    private  String title;
    //文章发布时间区间
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    //文章描述
    private String description;
    //文章分类id
    private Long categoryId;
    //文章标签id
    private List<Long> tagIds;
    //当前页码数
    private int currentPage;
    //每页大小
    private int pageSize;
}
