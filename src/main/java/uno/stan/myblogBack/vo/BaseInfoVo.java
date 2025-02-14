package uno.stan.myblogBack.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uno.stan.myblogBack.entity.Maxim;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseInfoVo implements Serializable {
    private String notice;
    private String avatar;
    private String signature;
    private String homepageCover;
    private String otherCover;
    private String qqCover;
    private String wechatCover;
    private List<Maxim> maximList;
}
