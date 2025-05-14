package homebar.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import homebar.com.common.R;
import homebar.com.entity.Cart;
import homebar.com.entity.Dish;
import homebar.com.mapper.CartMapper;
import homebar.com.service.CartService;
import homebar.com.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private DishService dishService;

    @Override
    public R addToCart(Integer userid, Integer dishid) {
        Cart cart = new Cart();
        if (userid != null) {
            cart.setUserId(userid);
        } else {
            return R.error("用户id为空，请确认是否登录");
        }
        if (dishid != null) {
            cart.setDishId(dishid);
        } else {
            return R.error("菜品id为空，请确认");
        }

        int count = cartMapper.insert(cart);

        if (count == 0){
            return R.error("菜品添加失败");
        }

        return R.success(cart,"菜品添加购物车成功"+"数量:"+count);
    }

    @Override
    public R getCartInfo(Integer userid) {

        //用来存放dishesId们
        List<Integer> dishesIds = new ArrayList<>();

        //查询userid绑定的所有dishId们
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        cartLambdaQueryWrapper.eq(Cart::getUserId,userid);
        for (Cart cart : cartService.getBaseMapper().selectList(cartLambdaQueryWrapper)) {
            dishesIds.add(cart.getDishId());
        }

        System.out.println(dishesIds);

        return R.success(dishesIds, "aaaaa");
    }
}
