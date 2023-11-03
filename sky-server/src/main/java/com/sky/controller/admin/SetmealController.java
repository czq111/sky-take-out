package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "套餐相关")
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @PostMapping
    @ApiOperation(value = "新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        setmealService.save(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("/套餐分页查询")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult res=setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(res);
    }

    @DeleteMapping
    @ApiOperation(value = "删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result deleteById(Long[] ids){
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation(value = "起售和停售")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result changeStatus(@PathVariable int status,Long id){
        setmealService.changeStatus(status,id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id){
        SetmealVO setmealVO=setmealService.getById(id);
        return  Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation(value = "修改套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result updateTc(@RequestBody SetmealDTO setmealDTO){
        setmealService.updateTc(setmealDTO);
        return Result.success();
    }
}
