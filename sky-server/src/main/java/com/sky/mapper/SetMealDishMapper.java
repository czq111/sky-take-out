package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    /**
     * 根据dish id 查询
     * @param args
     * @return
     */
    List<SetmealDish> queryByDishId(Long[] args);

    void insertBatch(List<SetmealDish> setmealDishes);


    @Delete("delete from setmeal_dish where setmeal_id=#{id}")
    void deleteBySetmealId(Long id);

    List<SetmealDish> queryBySetMealId(Long[] args);
}
