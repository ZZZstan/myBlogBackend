package uno.stan.myblogBack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Talk implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    //说说id
    @TableId(type = IdType.AUTO)
    private Long id;

    //说说内容
    private String content;

    //说说发布时间
    private  LocalDateTime createdTime;

    //说说状态
    private Long status;
}
