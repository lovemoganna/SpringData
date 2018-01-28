package com.luoyupiaoshang.service;

import com.springdata.domain.Employee;
import com.springdata.service.EmployeeService;
import com.springdata.service.EmployeeCrudService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ligang
 * date: 2018/1/27
 * time: 19:41
 */
public class EmployeeServiceTest {
    private ClassPathXmlApplicationContext context = null;
    private EmployeeService employeeService = null;
    private EmployeeCrudService employee;

    @Before
    public void setup() {
        context = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeService = context.getBean(EmployeeService.class);
        employee = context.getBean(EmployeeCrudService.class);
        System.out.println("setup");
    }

    @Test
    public void test() {
        employeeService.update("wangxiaoer", 1);
    }

    @Test
    public void test2() {
        List<Employee> employees = new ArrayList<>();
        Employee e = null;
        for (int i = 0; i < 100; i++) {
            e = new Employee();
            e.setName("name" + i);
            e.setAge(100 - i);
            employees.add(e);
        }
        employee.save(employees);
    }

    @Test
    public void test3() {
    }

    @After
    public void tearDown() {
        context = null;
        System.out.println("tearDown");
    }
}
