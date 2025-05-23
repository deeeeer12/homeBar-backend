package homebar.com.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import homebar.com.entity.Dish;
import homebar.com.entity.DishItem;
import lombok.Data;


@Data
@TableName("dish_items")
public class dishMoreInfoDTO extends DishItem {

    private Dish dish;


}
