package cool.cxc.app.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
    * 用户名称
    */
    @NotEmpty(message = "用户名称不能为空")
    @Size(min = 6, max = 12, message = "账号长度为 6-12 位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "用户名称格式为数字或字母")
    private String username;
    /**
    * 密码
    */
    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 18, message = "密码长度为 6-18 位")
    private String password;
    private String userpic;


    @Size(min = 11, max = 11, message = "手机号只能为11位")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String phone;
    @Email
    private String email;
    private int status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;
}
