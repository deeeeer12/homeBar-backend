package homebar.com.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("dish_items")
public class DishItem {

    private Integer id;

    private Integer dishId;  // 关联的Dish实体

    private Integer type;

    private String recipe;

    private BigDecimal alcoholContent;  // 酒精含量百分比

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
