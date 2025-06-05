package homebar.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import homebar.com.common.BusinessException;
import homebar.com.common.R;
import homebar.com.entity.Cart;
import homebar.com.entity.Dish;
import homebar.com.mapper.CartMapper;
import homebar.com.mapper.DishMapper;
import homebar.com.service.CartService;
import homebar.com.service.DishService;
import homebar.com.service.OrderService;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishMapper dishMapper;

    @Override
    public R addToCart(String openid, Integer dishid) {
        Cart cart = new Cart();
        if (openid != null) {
            //根据dishId到dishes表中查询对应的餐品名称
            LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishLambdaQueryWrapper.eq(Dish::getId,dishid);
            Dish dish = dishService.getOne(dishLambdaQueryWrapper);
            cart.setDishName(dish.getName());
            cart.setUserId(openid);
        } else {
            return R.error("用户id为空，请确认是否登录");
        }

        //查询该菜品是否已经点过，避免重复下单
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        cartLambdaQueryWrapper.eq(Cart::getUserId,openid)
                .eq(Cart::getDishId,dishid);
        Cart one = cartService.getOne(cartLambdaQueryWrapper);
        if (one != null){
            return R.error("已添加过该餐品，请勿重复添加");
        }

        if (dishid != null) {
            cart.setDishId(dishid);
            cart.setUserId(openid);
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
    public R getCartInfo(String openId) {

       List<Dish> dishList = getDishesByUserId(openId);

        return R.success(dishList,"获取购物车信息成功");
    }

    @Override
    public R delCartDish(String openId, Integer dishId) {

        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        cartLambdaQueryWrapper.eq(Cart::getUserId,openId)
                .eq(Cart::getDishId,dishId);
        boolean cartDishRemoved = cartService.remove(cartLambdaQueryWrapper);
        if (!cartDishRemoved) {
            throw new BusinessException("订单明细保存失败，菜品ID：" + dishId + "，" + "用户ID：" + openId);
        }

        return R.success(null,"删除餐品成功");
    }

    public List<Dish> getDishesByUserId(String userid){
        //用来存放dishesId们
        List<Integer> dishesIds = new ArrayList<>();

        //查询userid绑定的所有dishId们
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        cartLambdaQueryWrapper.eq(Cart::getUserId,userid);

        for (Cart cart : cartService.getBaseMapper().selectList(cartLambdaQueryWrapper)) {
            dishesIds.add(cart.getDishId());
        }

        //用于存放dish对象
        //根据dishIds查询菜品信息
        List<Dish> dishes = new ArrayList<>();

        for (int i = 0; i < dishesIds.size(); i++) {
            LambdaQueryWrapper<Dish> dishQW = new LambdaQueryWrapper<>();
            dishQW.eq(Dish::getId,dishesIds.get(i));
            Dish dish = dishService.getOne(dishQW);
            dishes.add(dish);
        }

        return dishes;
    }
}
