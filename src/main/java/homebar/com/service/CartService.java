package homebar.com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import homebar.com.common.R;
import homebar.com.entity.Cart;

public interface CartService extends IService<Cart> {
    R addToCart(String openId, Integer dishId);

    R getCartInfo(String openId);

    R delCartDish(String openId, Integer dishId);
}
