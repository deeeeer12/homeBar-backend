package homebar.com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import homebar.com.entity.DishItem;
import homebar.com.mapper.DishItemMapper;
import homebar.com.service.DishItemService;
import org.springframework.stereotype.Service;

@Service
public class DishItemServiceImpl extends ServiceImpl<DishItemMapper, DishItem> implements DishItemService {
}
