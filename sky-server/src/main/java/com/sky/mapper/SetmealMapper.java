package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    @AutoFill(value = OperationType.INSERT)
    void save(Setmeal setmeal);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @Select("select status from setmeal where id=#{id}")
    int getStatusById(Long id);

    @Delete("delete from setmeal where id=#{id}")
    void deleteById(Long id);

    @Update("update setmeal set status=#{status} where id=#{id}")
    void changeStatus(int status, Long id);

    @Select("select * from setmeal where id=#{id}")
    Setmeal getById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void updateTc(Setmeal setmeal);
}
