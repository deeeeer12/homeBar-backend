package homebar.com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("carts")
public class Cart {
    private Long id;
    private Long userId;
    private Long dishId;
//    private int quantity;
    // getters and setters
}
