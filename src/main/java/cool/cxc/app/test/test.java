package cool.cxc.app.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("/aa")
    public String test() {
        return "ss";
    }

}
