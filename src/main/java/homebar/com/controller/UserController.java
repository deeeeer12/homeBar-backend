package homebar.com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import homebar.com.common.R;
import homebar.com.entity.User;
import homebar.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/homebar/client/api")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 通用登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody User user){

        R login = userService.login(user);

        return login;

    }

    /**
     * 微信登录
     * @param body
     * @return
     */
//    @PostMapping("wxLogin")
//    public Map<String,Object> wxLogin(@RequestBody Map<String,String> request){
//        String code = request.get("code");
//        String appId = "wx1d3d66479ce9bc89";
//        String secret = "5552242cc90d7b7e3193261627e41ce7";
//
//        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId
//                + "&secret=" + secret
//                + "&js_code=" + code
//                + "&grant_type=authorization_code";
//
//        RestTemplate restTemplate = new RestTemplate();
//        //接收返回信息
//        String response = restTemplate.getForObject(url, String.class);
//
//        // 解析返回 JSON
//        JSONObject json = JSONObject.parseObject(response);
//        String openid = json.getString("openid");
//        String sessionKey = json.getString("session_key");
//
//        //TODO可以生成自己的token，进行用户绑定逻辑
//
//        //返回给前端
//
//    }

    /**
     * 根据openId获取用户的角色，是admin才可以在"我的"页面访问管理员界面。
     * @param openId
     * @return
     */
    @GetMapping("/getUserRole")
    public R getUserRole(String openId){

        R result = userService.getUserRole(openId);

        return result;
    }

}
