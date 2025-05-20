package homebar.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import homebar.com.common.R;
import homebar.com.entity.User;
import homebar.com.mapper.UserMapper;
import homebar.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserService userService;

    @Override
    public R login(User user) {
        LambdaQueryWrapper<User> userQw = new LambdaQueryWrapper<>();
        userQw.eq(User::getUsername,user.getUsername());

        User someone = userService.getOne(userQw);
        if (someone!=null){
            return R.success(someone,"登录成功");
        }else {
            return R.error("登陆失败");
        }
    }

    @Override
    public R getUserRole(String openId) {

        User user = userService.getById(openId);
        if (user == null){
            return R.error("为查询到该用户：" + openId);
        }

        return R.success(user.getRole(),"查询用户角色成功");
    }
}
