package homebar.com.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("orders")
public class Order {
    private Integer id;
    private String userId;
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private List<OrderItem> items; //订单里的餐品信息

    @TableField(exist = false)
    private String userName;
    // getters and setters

    }
