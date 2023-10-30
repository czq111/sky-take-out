package com.sky.service.impl;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImp implements DishService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    /**
     * 新增菜品
     * @param dishDTO
     */
    public void save(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
     //向菜品表插入一条数据
        dishMapper.save(dish);

        List<DishFlavor> flavors = dishDTO.getFlavors();

        Long dishId=dish.getId();
        if(flavors!=null && flavors.size()>0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
//            向口味表插入n条数据
            dishFlavorMapper.insertBatch(flavors);
        }

    }
}
