package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
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
    @Autowired
    SetMealDishMapper setMealDishMapper;

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

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> dishs=dishMapper.pageQuery(dishPageQueryDTO);
        List<DishVO> result = dishs.getResult();
        long total = dishs.getTotal();
        return new PageResult(total,result);
    }

    /**
     * 删除菜品
     * @param args
     */
    public void deleteById(Long[] args) {
        for (int i = 0; i < args.length; i++) {
            //查询菜品状态
            int status=dishMapper.queryStatusById(args[i]);
            if(status== StatusConstant.ENABLE)
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }
        List<SetmealDish> setmealDishes=setMealDishMapper.queryByDishId(args);
        if(setmealDishes!=null && setmealDishes.size()>0)
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);

        for (int i = 0; i <args.length ; i++) {
            dishMapper.deleteById(args[i]);
            dishFlavorMapper.deleteByDishId(args[i]);
        }


    }
}
