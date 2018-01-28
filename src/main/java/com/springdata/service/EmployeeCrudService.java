package com.springdata.service;

import com.springdata.domain.Employee;
import com.springdata.repository.EmployeeCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author: ligang
 * date: 2018/1/28
 * time: 11:22
 */
@Service
public class EmployeeCrudService {
    @Autowired
    private EmployeeCrudRepository employeeCrudRepository;

    @Transactional(rollbackOn = Exception.class)
    public void save(List<Employee> employees){
        employeeCrudRepository.save(employees);
    }
}
