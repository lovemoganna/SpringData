package com.luoyupiaoshang.springdatatest;

import com.springdata.domain.Employee;
import com.springdata.repository.EmployeeRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ligang
 * date: 2018/1/25
 * time: 16:11
 */
public class SpringDataTest {
    private ClassPathXmlApplicationContext context;
    private EmployeeRepository employeeRepository;
    private Employee employee;
    @Before
    public void Setup(){
        context = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeRepository = context.getBean(EmployeeRepository.class);
        System.out.println("setup");
    }
    @Test
    public void test1(){
       /* System.out.println(employeeRepository);
        employee = employeeRepository.findByName("xiaoming");*/
        /*List<Employee> list = employeeRepository.findByNameEndingWithAndAgeLessThan("ong", 30);*/

        List<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test4");
        List<Employee> employees = employeeRepository.findByNameInOrAgeLessThan(list, 22);
        for (Employee employee : employees) {
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
        }
    }
    @Test
    public void test2(){
        List<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test4");
        List<Employee> employees = employeeRepository.findByNameInAndAgeLessThan(list, 22);
        for (Employee employee : employees) {
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
        }
    }

    @Test
    public void test3(){
        Employee employee = employeeRepository.getEmployeeById();
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
    }

    @Test
    public void test4(){
        List<Employee> list = employeeRepository.queryParams("xiaoming", 10);
        for (Employee employee : list) {
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
        }
    }
    @Test
    public void test5(){
        List<Employee> list = employeeRepository.queryParams2("xiaoming", 10);
        for (Employee employee : list) {
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
        }
    }
    @Test
    public void test6(){
        List<Employee> list = employeeRepository.queryByLike2("test");
        for (Employee employee : list) {
            System.out.println("ID是"+employee.getId()+" 姓名是:"+employee.getName()+" 年龄是"+employee.getAge());
        }
    }

    /**
     * 本地查询的测试
     */
    @Test
    public void test7(){
        Long count = employeeRepository.getCount();
        System.out.println(count);
    }

    @After
    public void tearDown(){
        context=null;
        System.out.println("tearDown");
    }
}
