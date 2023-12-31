package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增菜品
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 删除菜品
     * @param args
     */
    @Delete("delete from dish where id=#{arg}")
    void deleteById(Long arg);


    /**
     * 根据id查询菜品状态
     * @param arg
     * @return
     */
    @Select("select status from dish where id =#{arg}")
    int queryStatusById(Long arg);

    /**
     * 批量删除
     * @param args
     */
    void deleteBatch(Long[] args);

    /**
     * 改变菜品状态
     * @param status
     * @param id
     */
    @Update("update dish set status=#{status} where id=#{id}")
    void updateStatus(int status, Long id);

    /**
     * 根据id查菜品
     * @param id
     * @return
     */
    @Select("select * from dish where id=#{id}")
    Dish queryById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    @Update("update dish set name=#{name}," +
            "category_id=#{categoryId}," +
            "price=#{price}," +
            "image=#{image}," +
            "description=#{description},status=#{status},update_time=#{updateTime},update_user=#{updateUser}" +
            " where id=#{id}")
    void updateDish(Dish dish);

    @Select("select * from dish where category_id=#{categoryId}")
    List<Dish> dishlist(Long categoryId);
    @Select("select * from dish where category_id=#{categoryId} and status=1")
    List<Dish> dishlist2(Long categoryId);
}
