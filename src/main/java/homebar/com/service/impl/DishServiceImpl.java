package homebar.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import homebar.com.common.R;
import homebar.com.entity.Dish;
import homebar.com.mapper.DishMapper;
import homebar.com.service.DishService;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Override
    public R selectAllDishes() {
        TreeSet<Dish> dishes = new TreeSet<>();

        QueryWrapper<Dish> dishQueryWrapper = new QueryWrapper<>();

        return null;
    }
}
