package homebar.com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import homebar.com.common.R;
import homebar.com.entity.Dish;

public interface DishService extends IService<Dish> {
    R selectAllDishes();
}
