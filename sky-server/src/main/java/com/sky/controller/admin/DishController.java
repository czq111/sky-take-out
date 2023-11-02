package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品管理")
public class DishController {

    @Autowired
    DishService dishService;
    @Autowired
    RedisTemplate redisTemplate;
    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}",dishDTO);
        dishService.save(dishDTO);
        String key="dish_"+dishDTO.getCategoryId();
        cleanCache(key);
        return Result.success();
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */

    @GetMapping("/page")
    @ApiOperation(value = "菜品分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){
        return Result.success(dishService.pageQuery(dishPageQueryDTO));
    }

    /**
     * 根据id删除菜品
     * @param args
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除菜品")
    public Result deleteById(Long[] ids){
        dishService.deleteById(ids);
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 改变菜品状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "改变菜品状态")
    public Result updateStatus(@PathVariable int status,Long id){
        dishService.updateStatus(status,id);
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 根据id查菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查菜品")
    public Result<DishVO> queryById(@PathVariable Long id){
        DishVO dishVO=dishService.queryById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改菜品")
    public Result updateDish(@RequestBody DishDTO dishDTO){
        dishService.updateDish(dishDTO);
        cleanCache("dish_*");
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value= "根据菜品分类查询菜品")
    public Result<List<Dish>> dishlist(Long categoryId){
        List<Dish> res=dishService.dishlist(categoryId);
        return Result.success(res);
    }

    //清除缓存
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
