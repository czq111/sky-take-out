package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImp implements SetmealService {

    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    SetMealDishMapper setMealDishMapper;
    @Autowired
    DishMapper dishMapper;
     /**
     * 新增套餐
     * @param setmealDTO
     */
    @Transactional
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.save(setmeal);
        Long id = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(id);
        });
        setMealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> setmeals=setmealMapper.pageQuery(setmealPageQueryDTO);
        long total = setmeals.getTotal();
        List<SetmealVO> result = setmeals.getResult();
        return new PageResult(total,result);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Transactional
    public void deleteBatch(Long[] ids) {
        for (int i = 0; i <ids.length ; i++) {
            int status=setmealMapper.getStatusById(ids[i]);
            if(status== StatusConstant.ENABLE)
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }
        for (int i = 0; i <ids.length ; i++) {
            setmealMapper.deleteById(ids[i]);
            setMealDishMapper.deleteBySetmealId(ids[i]);
        }
    }

    /**
     * 改变套餐状态
     * @param status
     * @param id
     */
    public void changeStatus(int status, Long id) {
        if(status==0){
            setmealMapper.changeStatus(status,id);
        }else {
            Long[] args=new Long[]{id};
            List<SetmealDish> setmealDishes = setMealDishMapper.queryBySetMealId(args);
            setmealDishes.forEach(setmealDish -> {
                int i = dishMapper.queryStatusById(setmealDish.getDishId());
                if(i==StatusConstant.DISABLE)
                    throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            });
            setmealMapper.changeStatus(status,id);
        }
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    public SetmealVO getById(Long id) {
        Setmeal setmeal=setmealMapper.getById(id);
        SetmealVO setmealVO=new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        List<SetmealDish> setmealDishes = setMealDishMapper.queryBySetMealId(new Long[]{setmeal.getId()});
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 修改套餐
     * @param setmealDTO
     */
    @Transactional
    public void updateTc(SetmealDTO setmealDTO) {
        Long id = setmealDTO.getId();
        setMealDishMapper.deleteBySetmealId(id);
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.updateTc(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(id);
        });
        setMealDishMapper.insertBatch(setmealDishes);
    }


    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

}
