package uno.stan.myblogBack.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseInfo implements Serializable {
    private String notice;
    private String avatar;
    private String signature;
    private String homepageCover;
    private String otherCover;
    private String qqCover;
    private String wechatCover;
}
