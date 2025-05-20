package homebar.com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("order_items")
public class OrderItem {
    private Integer id;
    private String dishName;
    private Integer orderId;
    private Integer dishId;
    // getters and setters
}
