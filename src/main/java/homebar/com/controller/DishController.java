package homebar.com.controller;

import homebar.com.common.R;
import homebar.com.entity.Dish;
import homebar.com.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/homebar/client/api")
public class DishController {

    @Autowired
    DishService dishService;

    @GetMapping("/getAllDishes")
    public R getAllDishes (){

        List<Dish> dishes = dishService.list();

        if (dishes != null){
            return R.success(dishes,"查询成功");
        }else {
            return R.error("未查询到菜品");
        }
    }

}
