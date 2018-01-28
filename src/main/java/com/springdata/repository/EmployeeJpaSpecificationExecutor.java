package com.springdata.repository;

import com.springdata.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author: ligang
 * date: 2018/1/28
 * time: 17:01
 * 继承多个接口,使其功能性更加强大
 */
public interface EmployeeJpaSpecificationExecutor extends JpaSpecificationExecutor<Employee>,JpaRepository<Employee,Integer>{
}

