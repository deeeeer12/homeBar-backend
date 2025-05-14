package homebar.com.controller;

import com.alibaba.fastjson.JSON;
import homebar.com.common.R;
import homebar.com.entity.User;
import homebar.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/homebar/client/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody User user){

        R login = userService.login(user);

        return login;

    }

}
