package cool.cxc.app.user.service;

import cool.cxc.app.common.bean.CommonResult;
import cool.cxc.app.user.bean.UserInfo;
import cool.cxc.app.user.model.UserModel;

import java.util.List;

public interface UserService {

    List<UserModel> findUserList();

    CommonResult generateAuthCode(String phone);

    CommonResult phoneLogin(String phone, String authCode);

    CommonResult login(String username, String password);

    CommonResult otherLogin(UserInfo userInfo);
}
