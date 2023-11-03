package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
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

    @Delete("delete from shopping_cart where user_id=#{userId}")
    void clean(Long userId);

    //void deleteById(Long userId, ShoppingCartDTO shoppingCartDTO);

    void sub(Long userId, Long dishId, Long setmealId, String dishFlavor, int number);

    void deleteById(Long userId, Long dishId, Long setmealId, String dishFlavor);

    //void sub(Long userId, ShoppingCartDTO shoppingCartDTO, int number);
}
