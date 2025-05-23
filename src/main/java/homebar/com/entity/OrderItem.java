package homebar.com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("order_items")
public class OrderItem {
    private String id;
    private String dishName;
    private String orderId;
    private Integer dishId;
    // getters and setters
}
