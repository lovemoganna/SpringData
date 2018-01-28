package com.luoyupiaoshang.springjdbctemplatetest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * author: ligang
 * date: 2018/1/24
 * time: 21:33
 */
public class SpringJdbcTemplateTest {
   private ClassPathXmlApplicationContext context;

    @Before
    public void testBefore() {
        context = new ClassPathXmlApplicationContext("beans-new.xml");
        System.out.println("先获得配置文件");
    }

    @Test
    public void testJdbcTemplate() {
        System.out.println("进入Test方法------");
       /* DataSource dataSource = (DataSource)context.getBean("dataSource");
        Assert.assertNotNull(dataSource);*/
        JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
        Assert.assertNotNull(jdbcTemplate);
    }

    @Test
    public void testSpringDataJpa(){
        System.out.println("我要自动建表了-------------");
    }

    @After
    public void fun() {
        //释放资源
        context=null;
        System.out.println("Test完毕!");

    }
}
