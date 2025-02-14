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
public class Tags implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)//声明是自增的,不然会用雪花算法随机一个id
    //标签id
    private Long id;

    //标签名称
    private String tagName;

}
