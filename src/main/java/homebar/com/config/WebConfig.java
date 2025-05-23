package homebar.com.config;

import homebar.com.common.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/homebar/client/api/**") // 需要认证的路径
                .addPathPatterns("/homebar/admin/api/**")
                .excludePathPatterns("/homebar/client/api/wxLogin") // 登录不拦截
                .excludePathPatterns("/homebar/client/api/register"); // 注册不拦截
    }
}
