package cool.cxc.app.user.service.impl;

import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import cool.cxc.app.common.GlobalException.ServerException;
import cool.cxc.app.common.Sms.SendSms;
import cool.cxc.app.common.bean.CommonResult;
import cool.cxc.app.common.redis.RedisUtil;
import cool.cxc.app.user.bean.UserInfo;
import cool.cxc.app.user.mapper.UserBindMapper;
import cool.cxc.app.user.mapper.UserMapper;
import cool.cxc.app.user.model.UserBindModel;
import cool.cxc.app.user.model.UserModel;
import cool.cxc.app.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserBindMapper userBindMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SendSms sendSms;

    @Override
    public List<UserModel> findUserList() {

        return userMapper.queryUserList();
    }

    @Override
    public CommonResult generateAuthCode(String phone) {
        // 判断是否已经发送过
        if (redisUtil.get(phone) != null)
            throw new ServerException("你操作太快了");
        // 生成四位验证码
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        // 发送验证码
        SendStatus[] sendStatuses = sendSms.sendSms(sb.toString(), phone);
        // 发送成功，写入redis
        if ("Ok".equals(sendStatuses[0].getCode())) {
            redisUtil.set(phone, sb, 60);
            log.info("验证码：{}",sb);
            return CommonResult.ok();
        } else {
            // 发送失败
            throw new ServerException("验证码发送失败");
        }
    }

    @Override
    public CommonResult phoneLogin(String phone, String authCode) {
        System.out.println(phone);
        System.out.println(authCode);
        if (redisUtil.hasKey(phone) && authCode.equals(redisUtil.get(phone))) {

            // 判断用户是否存在
            UserModel user = userMapper.queryUserByPhone(phone);
            System.out.println(user);
            if (user == null) {
                System.out.println("不存在");
                // 不存在 注册
                user = UserModel.builder().
                        username(phone).
                        createTime(new Date()).
                        password(phone).
                        status(1).
                        phone(phone).
                        build();

                userMapper.addUser(user);
                return CommonResult.ok(createSaveToken(user, 0));
            }
            System.out.println("存在");
            // 存在 检测用户否被禁用
            if (checkStatus(user.getStatus()))
                return CommonResult.ok(createSaveToken(user, 0));
            return null;


        } else {
            throw new ServerException("验证码已过期，请重新获取验证码");
        }

    }

    @Override
    public CommonResult login(String username, String password) {
        Map<String, String> usernameMap = filterUserData(username);
        log.info(String.valueOf(usernameMap));
        UserModel user = null;
        if (usernameMap.containsKey("phone")) {
            user = userMapper.queryUserByPhone(username);
        } else if (usernameMap.containsKey("email")) {
            user = userMapper.queryUserByEmail(username);
        } else if (usernameMap.containsKey("username")) {
            user = userMapper.queryUserByUserName(username);
        }

        if (user == null) {
            throw new ServerException("昵称/邮箱/手机号错误");
        }
        // 用户否被禁用
        if (checkStatus(user.getStatus())) {
            // 验证密码
            if (password.equals(user.getPassword())) return CommonResult.ok(createSaveToken(user, 0));
            else throw new ServerException("密码错误");
        }

        return null;
    }

    @Override
    public CommonResult otherLogin(UserInfo userInfo) {
        // 解密
        // 验证用户是否存在
        UserBindModel userBind = userBindMapper.queryUserBindByTypeAndOpenId(userInfo.getProvider(), userInfo.getOpenid());
        // 用户不存在，创建用户
        if (userBind == null) {
            userBind = UserBindModel.builder().
                    type(userInfo.getProvider()).
                    openid(userInfo.getOpenid()).
                    nickname(userInfo.getNickName()).
                    avatarurl(userInfo.getAvatarUrl()).
                    build();
            userBindMapper.addUserBind(userBind);
            return CommonResult.ok(createSaveToken(userBind, userInfo.getExpires_in()));
        }

        // 判断用户是否绑定手机
        if (userBind.getUserId() < 1) {
            return CommonResult.ok(createSaveToken(userBind, userInfo.getExpires_in()));
        }
        // 用户否被禁用
        // 存在 检测用户否被禁用
        UserModel user = userMapper.queryUserById(userBind.getUserId());
        if (user != null) {
            if (checkStatus(user.getStatus())) {
                // 登录成功返回token
                return CommonResult.ok(createSaveToken(user, userInfo.getExpires_in()));
            }
        }
        return null;

    }

    // 验证用户名是什么格式（昵称/邮箱/手机号）
    public Map<String, String > filterUserData(String username) {
        Map<String, String> map = new HashMap<>();
        // 验证是否是手机号
        if (username.matches("^[1][3,4,5,6,7,8,9][0-9]{9}$")) {
            map.put("phone", username);
            return map;
        }

        // 邮箱
        if (username.matches("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?.)+[a-z]{2,}$")) {
            map.put("email", username);
            return map;
        }

        // 昵称
        map.put("username", username);
        return map;
    }

    // 生成登录token
    public String createSaveToken(Object o, long expires_in) {
        String token = UUID.randomUUID().toString();
        redisUtil.set(o.toString(), token, expires_in == 0 ? 3600 : expires_in);

        return token;
    }

    // 检测用户是否被禁用
    public boolean checkStatus(int status) {
        if (status == 1) {
            return true;
        }
        throw new ServerException("该用户已被禁用");
    }
}
