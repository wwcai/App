package cool.cxc.app.user.mapper;

import cool.cxc.app.user.model.UserBindModel;
import cool.cxc.app.user.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserMapper {
    List<UserModel> queryUserList();

    UserModel queryUserByPhone(String phone);

    int addUser(UserModel userModel);

    UserModel queryUserByEmail(String username);

    UserModel queryUserByUserName(String username);

    UserModel queryUserById(int id);


}
