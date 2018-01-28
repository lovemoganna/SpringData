package com.luoyupiaoshang.service;

import com.springdata.domain.Employee;
import com.springdata.repository.EmployeeJpaRepository;
import com.springdata.repository.EmployeeJpaSpecificationExecutor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author: ligang
 * date: 2018/1/28
 * time: 17:09
 * 目标:
 * 1.分页
 * 2.排序
 * 3.查询条件:age>50
 */
public class EmployeeJpaSpecificationRepositoryTest {
    private ClassPathXmlApplicationContext context = null;
    private EmployeeJpaSpecificationExecutor employeeJpaSpecificationExecutor = null;

    @Before
    public void setup() {
        context = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeJpaSpecificationExecutor = context.getBean(EmployeeJpaSpecificationExecutor.class);
        System.out.println("setup");
    }

    /**
     * 按照id降序查询 第一页 5条 年龄 > 50 的数据
     */
    @Test
    public void test() {
        //按照id降序查询
        Sort.Order id = new Sort.Order(Sort.Direction.DESC, "id");
        Sort orders = new Sort(id);

        //设置按第一页查询,查询的条数为5条
        PageRequest pageRequest = new PageRequest(0, 5, orders);

        /**
         * root:就是我们要查询的类型(Employee)
         * 关系如下:root(employee(age)),从这个路径下就可以取到age属性.
         * query:添加查询条件
         * db:构建Predicate
         *
         * 下面做的事是设置查询>50 岁Employee
         * 主要用下面这种方式来进行更加细致的操作
         */
        Specification<Employee> employeeSpecification = new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Path path = root.get("age");
                return cb.gt(path,50);
            }
        };

        Page<Employee> list = employeeJpaSpecificationExecutor.findAll(employeeSpecification, pageRequest);
        System.out.println(list.getContent());
    }


    @After
    public void tearDown() {
        context = null;
        System.out.println("tearDown");
    }

}
