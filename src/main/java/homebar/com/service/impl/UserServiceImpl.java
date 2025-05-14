package homebar.com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import homebar.com.entity.User;
import homebar.com.mapper.UserMapper;
import homebar.com.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
