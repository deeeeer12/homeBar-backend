package homebar.com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("order_items")
public class OrderItem {
    private Long id;
    private Long orderId;
    private Long dishId;
    private int quantity;
    // getters and setters
}
