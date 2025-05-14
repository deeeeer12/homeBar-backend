package homebar.com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("homebar.com.mapper")
@SpringBootApplication
@EnableTransactionManagement//开启事务
public class HomeBarApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeBarApplication.class, args);
    }
}
