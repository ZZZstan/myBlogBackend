package uno.stan.myblogBack.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebInfoVo implements Serializable {
    public final static LocalDate WEB_CREATED_TIME= LocalDate.of(2024, 11, 14);

    //运行时间
    private Long runTime;

    //文章数目
    private Long articleNum;

    //标签数目
    private Long tagNum;

    //分类数目
    private Long categoryNum;

    //访问次数
    private Long visits;

    //用户数目
    private Long userNum;

}
