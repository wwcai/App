package cool.cxc.app.user.mapper;

import cool.cxc.app.user.model.UserBindModel;
import cool.cxc.app.user.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserBindMapper {


    int addUserBind(UserBindModel userBind);

    UserBindModel queryUserBindByTypeAndOpenId(String provider, String openid);
}
