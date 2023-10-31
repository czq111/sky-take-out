package com.sky.mapper;

import com.sky.entity.SetmealDish;
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
}
