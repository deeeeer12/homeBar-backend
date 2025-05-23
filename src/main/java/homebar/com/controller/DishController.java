package homebar.com.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import homebar.com.common.R;
import homebar.com.dto.dishMoreInfoDTO;
import homebar.com.entity.Dish;
import homebar.com.entity.DishItem;
import homebar.com.service.DishItemService;
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

    @Autowired
    DishItemService dishItemService;

    @GetMapping("/getAllDishes")
    public R getAllDishes (){

        List<Dish> dishes = dishService.list();

        if (dishes != null){
            return R.success(dishes,"查询成功");
        }else {
            return R.error("未查询到菜品");
        }
    }

    /**
     * 根据餐品ID查询餐品的详细信息
     * @param dishId
     * @return
     */
    @GetMapping("/getDishInfoById")
    public R getDishInfoById(Integer dishId){

        Dish dish = dishService.getById(dishId);
        if (dish == null){
            return R.error("查询失败");
        }

        LambdaQueryWrapper<DishItem> dishItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishItemLambdaQueryWrapper.eq(DishItem::getDishId,dishId);
        DishItem dishItem = dishItemService.getOne(dishItemLambdaQueryWrapper);

        dishMoreInfoDTO dishMoreInfoDTO = new dishMoreInfoDTO();
        dishMoreInfoDTO.setDish(dish);
        dishMoreInfoDTO.setRecipe(dishItem.getRecipe());
        dishMoreInfoDTO.setType(dishItem.getType());
        dishMoreInfoDTO.setAlcoholContent(dishItem.getAlcoholContent());


        return R.success(dishMoreInfoDTO,"查询成功");
    }

}
