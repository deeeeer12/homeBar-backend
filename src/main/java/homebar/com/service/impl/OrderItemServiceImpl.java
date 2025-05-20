package homebar.com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import homebar.com.entity.OrderItem;
import homebar.com.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements homebar.com.service.OrderItemService {
}
