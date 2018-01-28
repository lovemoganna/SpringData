package com.springdata.repository;

import com.springdata.domain.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * @author: ligang
 * date: 2018/1/28
 * time: 11:19
 */
public interface EmployeeCrudRepository extends CrudRepository<Employee,Integer> {
}
