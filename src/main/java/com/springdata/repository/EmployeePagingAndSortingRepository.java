package com.springdata.repository;

import com.springdata.domain.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author: ligang
 * date: 2018/1/28
 * time: 14:31
 */
public interface EmployeePagingAndSortingRepository extends PagingAndSortingRepository<Employee,Integer> {

}
