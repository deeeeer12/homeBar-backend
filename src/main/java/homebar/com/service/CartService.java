package homebar.com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import homebar.com.common.R;
import homebar.com.entity.Cart;

public interface CartService extends IService<Cart> {
    R addToCart(Integer userid, Integer dishid);

    R getCartInfo(Integer userid);
}
