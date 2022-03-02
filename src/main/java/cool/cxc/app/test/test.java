package cool.cxc.app.test;

import cool.cxc.app.user.model.UserModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class test {

    @PostMapping("/aa")
    public void test(@Valid UserModel user) {
        System.out.println(user);
    }

}
