package homebar.com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import homebar.com.common.R;
import homebar.com.dto.orderStatusDTO;
import homebar.com.entity.Order;
import homebar.com.entity.OrderItem;
import homebar.com.entity.User;
import homebar.com.service.OrderItemService;
import homebar.com.service.OrderService;
import homebar.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/homebar/admin/api")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/getAllOrders")
    public R getAllOrders(){
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.orderByAsc(Order::getCreatedAt);
        List<Order> orders = orderService.getBaseMapper().selectList(orderLambdaQueryWrapper);

        for (Order order : orders) {
            //根据订单ID查询items表中所有跟这个订单ID关联的餐品信息，并添加到items列表中。
            LambdaQueryWrapper<OrderItem> orderItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderItemLambdaQueryWrapper.eq(OrderItem::getOrderId,order.getId());
            List<OrderItem> orderItems = orderItemService.getBaseMapper().selectList(orderItemLambdaQueryWrapper);
            if (orderItems != null){
                order.setItems(orderItems);
            }

            //根据openId查询用户名userName
            User user = userService.getById(order.getUserId());
            order.setUserName(user.getUsername());
        }
        return  R.success(orders,"查询所有订单成功");
    }

    @PostMapping("/updateOrderStatus")
    public R updateOrderStatus(@RequestBody orderStatusDTO dto){

        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
        orderUpdateWrapper.eq("id",dto.getOrderId());

        Order updateOrder = new Order();
        updateOrder.setStatus(dto.getStatus());
        updateOrder.setUpdatedAt(LocalDateTime.now());
        boolean orderStatusUpdated = orderService.update(updateOrder,orderUpdateWrapper);
        if (!orderStatusUpdated){
            return R.error("更新订单状态失败");
        }
        return R.success(null,"更新成功");
    }

    @DeleteMapping("/deleteOrder")
    public R deleteOrder(Integer orderId) {
        boolean removed = orderService.removeById(orderId);
        if (removed) {
            return R.success(null,"订单删除成功");
        } else {
            return R.error("订单删除失败");
        }
    }
}
