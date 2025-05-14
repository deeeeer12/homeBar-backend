package homebar.com.controller;

import homebar.com.common.R;
import homebar.com.entity.Dish;
import homebar.com.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R addToCart(Integer userid,Integer dishid){

        R result = cartService.addToCart(userid,dishid);

        return result;
    }

    @GetMapping("/getCartInfo")
    public R getCartInfo(Integer userid){

        R result = cartService.getCartInfo(userid);

        return null;
    }

}
