package homebar.com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("carts")
public class Cart {
    private Integer id;
    private Integer userId;
    private Integer dishId;
//    private int quantity;
    // getters and setters
}
