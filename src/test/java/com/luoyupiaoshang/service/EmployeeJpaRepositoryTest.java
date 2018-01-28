package com.luoyupiaoshang.service;

import com.springdata.domain.Employee;
import com.springdata.repository.EmployeeJpaRepository;
import com.springdata.repository.EmployeePagingAndSortingRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author: ligang
 * date: 2018/1/28
 * time: 16:38
 */
public class EmployeeJpaRepositoryTest {
    private ClassPathXmlApplicationContext context=null;
    private EmployeeJpaRepository employeeJpaRepository =null;
    @Before
    public void setup(){
        context = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeJpaRepository = context.getBean(EmployeeJpaRepository.class);
        System.out.println("setup");
    }

    @Test
    public void test(){
        //查询ID为99的Employee内容
        Employee one = employeeJpaRepository.findOne(99);
        System.out.println(one);

        //检查id=?的内容是否存在
        System.out.println(employeeJpaRepository.exists(2));
        System.out.println(employeeJpaRepository.exists(122));
        Sort.Order id = new Sort.Order(Sort.Direction.DESC, "id");
        Sort orders = new Sort(id);
        List<Employee> list = employeeJpaRepository.findAll(orders);
        for (Employee employee : list) {
            System.out.println(employee);
        }
        System.out.println(employeeJpaRepository.count());
    }


    @After
    public void tearDown(){
        context=null;
        System.out.println("tearDown");
    }

}
