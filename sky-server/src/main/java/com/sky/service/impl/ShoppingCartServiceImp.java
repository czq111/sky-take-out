package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImp implements ShoppingCartService {
    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    ShoppingCartMapper shoppingCartMapper;
    @Autowired
    DishMapper dishMapper;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    public void add(ShoppingCartDTO shoppingCartDTO) {
        String dishFlavor = shoppingCartDTO.getDishFlavor();
        Long dishId = shoppingCartDTO.getDishId();
        Long setmealId = shoppingCartDTO.getSetmealId();
        ShoppingCart shoppingCart = new ShoppingCart();
        //购物车添加的是套餐
        if(setmealId!=null){
            Setmeal setmeal = setmealMapper.getById(setmealId);
            Long userId = BaseContext.getCurrentId();
            ShoppingCart shoppingCart1=shoppingCartMapper.queryByUserId(userId,setmeal.getId());
            //如果购物车已经存在
            if(shoppingCart1!=null){
                Integer number = shoppingCart1.getNumber()+1;
                shoppingCartMapper.addOne(userId,setmeal.getId(),number);
                return;
            }
            //如果购物车不存在，第一次创建
           // BeanUtils.copyProperties(setmeal,shoppingCart);
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setNumber(1);
            shoppingCart.setAmount(setmeal.getPrice());
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setImage(setmeal.getImage());
            shoppingCart.setSetmealId(setmeal.getId());
            shoppingCartMapper.add(shoppingCart);
            return;
        }
        //购物车添加的是商品
        Dish dish = dishMapper.queryById(dishId);
        //如果购物车已经存在该商品
        ShoppingCart shoppingCart1=shoppingCartMapper.queryByUserId2(BaseContext.getCurrentId(),dishId,dishFlavor);
        //如果购物车已经存在
        if(shoppingCart1!=null){
            Integer number = shoppingCart1.getNumber()+1;
            shoppingCartMapper.addOne2(BaseContext.getCurrentId(),dishId,dishFlavor,number);
            return;
        }
        //如果购物车不存在，第一次创建
        // BeanUtils.copyProperties(setmeal,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCart.setNumber(1);
        shoppingCart.setAmount(dish.getPrice());
        shoppingCart.setName(dish.getName());
        shoppingCart.setImage(dish.getImage());
        shoppingCart.setDishId(dish.getId());
        if(dishFlavor!=null||dishFlavor!="")
            shoppingCart.setDishFlavor(dishFlavor);
        shoppingCartMapper.add(shoppingCart);
        return;
    }

    /**
     * 查看购物车
     * @return
     */
    public List<ShoppingCart> list() {
        List<ShoppingCart> shoppingCarts=shoppingCartMapper.list(BaseContext.getCurrentId());
        return shoppingCarts;
    }
}
