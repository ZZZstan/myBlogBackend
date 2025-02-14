package uno.stan.myblogBack.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalkVo implements Serializable {
    //说说id
    @TableId(type = IdType.AUTO)
    private Long id;

    //说说内容
    private String content;

    //图片地址
    private String[] imgUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //说说发布时间
    private LocalDateTime createdTime;

    //说说状态
    private Long status;
}
