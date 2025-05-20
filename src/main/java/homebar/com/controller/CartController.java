package homebar.com.controller;

import homebar.com.common.R;
import homebar.com.dto.addToCartDTO;
import homebar.com.entity.Dish;
import homebar.com.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/homebar/client/api")
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * 添加菜品到购物车中
     * @param userid
     * @param dishid
     * @return
     */
    @PostMapping("/addToCart")
    public R addToCart(@RequestBody addToCartDTO dto){

        R result = cartService.addToCart(dto.getOpenId(),dto.getDishId());

        return result;
    }

    /**
     * 获取购物车信息
     * @param openId
     * @return
     */
    @GetMapping("/getCartInfo")
    public R getCartInfo(String openId){

        R result = cartService.getCartInfo(openId);

        return result;
    }

    /**
     * 删除购物车中的餐品
     * @param dto
     * @return
     */
    @PostMapping("/delCartDish")
    public R delCartDish(@RequestBody addToCartDTO dto){

        R result = cartService.delCartDish(dto.getOpenId(),dto.getDishId());

        return result;
    }

}
