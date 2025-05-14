package homebar.com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import homebar.com.entity.Cart;
import homebar.com.mapper.CartMapper;
import homebar.com.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
}
