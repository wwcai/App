package cool.cxc.app.user.controller;

import cool.cxc.app.common.bean.CommonResult;
import cool.cxc.app.user.bean.UserInfo;
import cool.cxc.app.user.mapper.UserMapper;
import cool.cxc.app.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Api("UserController")
@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @ApiOperation("手机号登录")
    @PostMapping("/phoneLogin")
    @ResponseBody
    public CommonResult phoneLogin(@RequestParam
                                       @Size(min = 11, max = 11, message = "手机号只能为11位")
                                       @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
                                       String phone,
                                 @RequestParam
                                       @Size(max = 4)
                                     String authCode) {
        return userService.phoneLogin(phone, authCode);
    }


    @ApiOperation("账号密码登录")
    @PostMapping("/login")
    public CommonResult login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @ApiOperation("第三方登录")
    @PostMapping("/otherLogin")
    public CommonResult otherLogin(@RequestBody UserInfo userInfo) {
        return userService.otherLogin(userInfo);
    }


    @ApiOperation(value = "获取验证码")
    @GetMapping("/getAuthCode")
    public CommonResult getAuthCode(@RequestParam
                                        @Size(min = 11, max = 11, message = "手机号只能为11位")
                                        @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
                                        String phone) {

        return userService.generateAuthCode(phone);
    }

    @GetMapping("/find")
    public CommonResult findUser() {
        CommonResult<?> cr = null;

        cr = CommonResult.ok(userMapper.queryUserByPhone("1232434"));

        return cr;
    }
}
