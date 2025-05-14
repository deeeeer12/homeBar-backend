package homebar.com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import homebar.com.entity.Order;
import homebar.com.mapper.OrderMapper;
import homebar.com.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
