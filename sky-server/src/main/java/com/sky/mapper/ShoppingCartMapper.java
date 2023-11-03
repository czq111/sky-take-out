package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    @Select("select * from shopping_cart where user_id=#{userId} and setmeal_id=#{id}")
    ShoppingCart queryByUserId(Long userId, Long id);

    @Update("update shopping_cart set number=#{number} where user_id=#{userId} and setmeal_id=#{id}")
    void addOne(Long userId, Long id, Integer number);


    void add(ShoppingCart shoppingCart);

    ShoppingCart queryByUserId2(Long userId, Long dishId, String dishFlavor);


    void addOne2(Long userId, Long dishId, String dishFlavor, Integer number);

    @Select("select * from shopping_cart where user_id=#{userId}")
    List<ShoppingCart> list(Long userId);
}
