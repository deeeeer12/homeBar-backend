package homebar.com.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import homebar.com.entity.User;
import homebar.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/homebar/client/api")
public class LoginController {

    @Autowired
    private StringRedisTemplate redisTemplate; // 用于存储 token -> openid 映射

    @Autowired
    private UserService userService;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @PostMapping("/wxLogin")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) throws Exception {

        // 调用微信 API 获取 openid 和 session_key
        String code = body.get("code");
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid +
                "&secret=" + secret +
                "&js_code=" + code +
                "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = JSONObject.parseObject(response);
        String openid = json.getString("openid");

        //openid为空，则该接口调用有问题，直接返回失败
        if (openid == null) {
            return ResponseEntity.status(401).body("登录失败，无法获取 openid");
        }

        //查询用户是否存在逻辑
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getId, openid);
        User user = userService.getOne(query);

        if (user != null){
            //已注册，生成token并返回
            // 创建自定义 token
            String token = UUID.randomUUID().toString();

            // 将 token 和 openid 存入 Redis，设置过期时间（如 2 小时）
            redisTemplate.opsForValue().set("token:" + token, openid, 2, TimeUnit.HOURS);

            // 返回 token 给前端
            Map<String, String> result = new HashMap<>();
            result.put("status", "200");
            result.put("msg", "用户："+user.getUsername()+"登录成功");
            result.put("token", token);
            result.put("openid", openid);
            return ResponseEntity.ok(result);
        }else {
            //未注册，返回特定状态（未注册标识）给前端，前端好去跳转到注册页面
            Map<String, String> result = new HashMap<>();
            result.put("status", "404"); // 自定义未注册状态码
            result.put("msg", "用户未注册");
            result.put("openid", openid);
            return ResponseEntity.ok(result);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser){
        //前端传openid和username等信息

        //判断是否存在
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getId, newUser.getId());
        User exist = userService.getOne(query);
        if (exist != null) {
            return ResponseEntity.badRequest().body("用户已存在");
        }

        System.out.println(newUser.getUsername());
        userService.save(newUser);

        // 注册成功后可以直接返回登录的token，或者提示注册成功
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("token:" + token, newUser.getId(), 2, TimeUnit.HOURS);

        Map<String, String> result = new HashMap<>();
        result.put("status", "200");
        result.put("msg", "注册成功");
        result.put("token", token);
        return ResponseEntity.ok(result);

    }
}
