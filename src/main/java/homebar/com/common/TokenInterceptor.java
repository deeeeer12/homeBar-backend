//package homebar.com.common;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class TokenInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader("Authorization");
//
//        if (token == null || token.isEmpty()) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return false;
//        }
//
//        String openid = redisTemplate.opsForValue().get("token:" + token);
//        if (openid == null) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return false;
//        }
//
//        // 可将 openid 存入 request 属性，方便后续使用
//        request.setAttribute("openid", openid);
//        return true;
//    }
//}
