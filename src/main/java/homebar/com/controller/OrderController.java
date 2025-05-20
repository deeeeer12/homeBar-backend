package homebar.com.controller;

import homebar.com.common.R;
import homebar.com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/homebar/client/api")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/takeOrder")
    public R takeOrder(String userid){

        R result = orderService.takeOrder(userid);
        return result;
    }

    @GetMapping("/getOrder")
    public R getOrder(String openId){

        R result = orderService.getOrder(openId);

        return result;
    }

}
