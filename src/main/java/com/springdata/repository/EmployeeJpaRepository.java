package com.springdata.repository;

import com.springdata.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: ligang
 * date: 2018/1/28
 * time: 16:34
 */
public interface EmployeeJpaRepository extends JpaRepository<Employee,Integer>{

}
