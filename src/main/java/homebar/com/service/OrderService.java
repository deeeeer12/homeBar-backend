package homebar.com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import homebar.com.common.R;
import homebar.com.entity.Order;

public interface OrderService extends IService<Order> {
    R takeOrder(String userid);

    R getOrder(String openId);
}
