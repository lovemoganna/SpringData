package com.luoyupiaoshang.service;

import com.springdata.domain.Employee;
import com.springdata.repository.EmployeePagingAndSortingRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author: ligang
 * date: 2018/1/28
 * time: 14:34
 */
public class PagingAndSortingRepositoryTest {
    private ClassPathXmlApplicationContext context=null;
    private EmployeePagingAndSortingRepository employeePagingAndSortingRepository =null;
    @Before
    public void setup(){
        context = new ClassPathXmlApplicationContext("beans-new.xml");
        employeePagingAndSortingRepository = context.getBean(EmployeePagingAndSortingRepository.class);
        System.out.println("setup");
    }

    /**
     * 分页
     */
    @Test
    public void testPage(){
        //page:当前页数,它是按照索引(index=0.....)来算的  size:每页显示的条数
        PageRequest pageable = new PageRequest(0,5);
        Page<Employee> page = employeePagingAndSortingRepository.findAll(pageable);
        System.out.println(page.getTotalPages());//一共有多少页
        System.out.println(page.getTotalElements());//总记录数
        System.out.println(page.getContent());//内容集合
        System.out.println(page.getNumber()+1);//当前是第1页,不过它是从第0页算起的.
        System.out.println(page.getSize());//每页的页数
        System.out.println(page.getSort());
        System.out.println(page.getNumberOfElements());
        /**
         *
         20
         100
         [com.springdata.domain.Employee@5707f613, com.springdata.domain.Employee@68b11545, com.springdata.domain.Employee@7d0100ea, com.springdata.domain.Employee@357bc488, com.springdata.domain.Employee@4ea17147]
         0
         5
         null
         5
         */
    }

    /**
     * 排序
     */
    @Test
    public void testSort(){
        //按照id字段降序排列
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = new Sort(order);
        PageRequest pageRequest = new PageRequest(0,5,sort);
        Page<Employee> page = employeePagingAndSortingRepository.findAll(pageRequest);

        System.out.println(page.getContent());
    //[Employee{id=100, name='name99', age=1}, Employee{id=99, name='name98', age=2}, Employee{id=98, name='name97', age=3}, Employee{id=97, name='name96', age=4}, Employee{id=96, name='name95', age=5}]
    }
    @After
    public void tearDown(){
        context=null;
        System.out.println("tearDown");
    }

}
