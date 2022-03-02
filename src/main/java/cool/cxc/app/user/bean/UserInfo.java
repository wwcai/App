package cool.cxc.app.user.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String provider;
    private String openid;
    private long expires_in;
    private String nickName;
    private String avatarUrl;
}
