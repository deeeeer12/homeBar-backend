package homebar.com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import homebar.com.common.R;
import homebar.com.entity.User;

public interface UserService extends IService<User> {
    R login(User user);

}
