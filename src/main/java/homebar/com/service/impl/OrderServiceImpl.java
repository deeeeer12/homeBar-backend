package homebar.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import homebar.com.common.BusinessException;
import homebar.com.common.R;
import homebar.com.entity.Cart;
import homebar.com.entity.Dish;
import homebar.com.entity.Order;
import homebar.com.entity.OrderItem;
import homebar.com.mapper.OrderMapper;
import homebar.com.service.CartService;
import homebar.com.service.DishService;
import homebar.com.service.OrderItemService;
import homebar.com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    @Override
    public R takeOrder(String userid) {
        //1.获取购物车中的数据;
        List<Dish> dishList = getDishesByUserId(userid);
        if (dishList == null || dishList.isEmpty()) {
            throw new BusinessException("购物车为空，无法下单");
        }

        //2.在order表中创建订单;
        Order order = new Order();
        order.setUserId(userid);
        order.setStatus("制作中");
        order.setCreatedAt(LocalDateTime.now());

        //2.1保存order订单到order表中
        boolean orderSaved = orderService.save(order);
        if (!orderSaved || order.getId() == null) {
            throw new BusinessException("订单保存失败");
        }

        //2.2获取刚创建的订单号
        Integer orderId = order.getId();
        //3.创建订单明细，并插入;
        for (Dish dish : dishList) {
            Integer dishId = dish.getId();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setDishId(dishId);
            orderItem.setDishName(dish.getName());
            boolean orderItemSaved = orderItemService.save(orderItem);
            if (!orderItemSaved) {
                throw new BusinessException("订单明细保存失败，菜品ID：" + dish.getId());
            }
        }

        //4.清空用户购物车
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",userid);
        boolean cartInfoRemoved = cartService.remove(cartQueryWrapper);
        if (!cartInfoRemoved) {
            throw new BusinessException("清空购物车失败");
        }

        return R.success(dishList,"下单成功！");

    }

    @Override
    public R getOrder(String openId) {
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getUserId,openId);
        //用户很可能有多个订单，不建议使用getOne
//        Order order = orderService.getOne(orderLambdaQueryWrapper);
        List<Order> orders = orderMapper.selectList(orderLambdaQueryWrapper);

        if (orders == null){
            return R.error("该用户还没有下单");
        }

        for (Order order : orders) {
            //根据订单ID查询items表中所有跟这个订单ID关联的餐品信息，并添加到items列表中。
            LambdaQueryWrapper<OrderItem> orderItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderItemLambdaQueryWrapper.eq(OrderItem::getOrderId,order.getId());
            List<OrderItem> orderItems = orderItemService.getBaseMapper().selectList(orderItemLambdaQueryWrapper);
            if (orderItems != null){
                order.setItems(orderItems);
            }
        }

        return R.success(orders,"查询订单成功");
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
