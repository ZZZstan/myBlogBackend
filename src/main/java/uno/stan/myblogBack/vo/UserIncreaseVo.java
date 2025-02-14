package uno.stan.myblogBack.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIncreaseVo implements Serializable {
    //注册时间
    private LocalDate registerTime;
    //增长人数
    private Long increaseNumber;
}
