package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);


    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    void insertEmp(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     * @param
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);


    /**
     * 启用和禁用员工账号
     * @param
     * @return
     */
    int status(Integer status, Long id);

    /**
     * 根据id查询员工
     * @param
     * @return
     */
    Employee getEmployeeById(Long id);

    /**
     * 编辑员工信息
     * @param
     * @return
     */
    void updateEmployee(EmployeeDTO employeeDTO);
}
