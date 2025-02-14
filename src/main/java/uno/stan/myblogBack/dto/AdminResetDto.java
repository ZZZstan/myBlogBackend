package uno.stan.myblogBack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResetDto implements Serializable {
    private String email;
    private String code;
    private String newPassword;
}
