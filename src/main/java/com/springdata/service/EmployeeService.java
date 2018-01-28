package com.springdata.service;

import com.springdata.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author: ligang
 * date: 2018/1/27
 * time: 19:39
 */
@Service
public class EmployeeService {
 @Autowired
 private EmployeeRepository employeeRepository;

 @Transactional(rollbackOn = Exception.class)
 public void update(String name,Integer id){
     employeeRepository.updateNameById(name,id);
 }

 @Transactional(rollbackOn = Exception.class)
 public void delete(Integer id){
  employeeRepository.deleteById(id);
 }

}
