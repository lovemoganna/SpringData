package com.luoyupiaoshang.springjdbctemplatetest;

import com.luoyupiaoshang.dao.StudentDAO;
import com.luoyupiaoshang.domain.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: ligang
 * date: 2018/1/24
 * time: 22:28
 */
public class SpringTemplateTest2 {
    private StudentDAO studentDAO;
    ClassPathXmlApplicationContext context=null;
    @Before
    public void fun1(){
        context = new ClassPathXmlApplicationContext("beans.xml");
        studentDAO =(StudentDAO) context.getBean("studentDAO");
        System.out.println("get classpathfile!");
    }

    /**
     * 测试SpringJDBCTemplate查询
     * @throws SQLException
     */
    @Test
    public void testSpringTemplateQuery() throws SQLException {
        List<Student> query = studentDAO.query();
        for (Student student : query) {
                System.out.println("ID:" + student.getId() + " " + "AGE:" + student.getAge() + " " + "NAME:" + student.getName());
            }
    }

    /**
     * 测试SpringJDBCTemplate插入
     */
    @Test
    public void testSpringTemplateSave(){
        Student student = new Student();
        student.setAge(12);
        student.setName("anna");
        studentDAO.save(student);
    }
    /**
     * 测试SpringJDBCTemplate删除
     */
    @Test
    public void testSpringTemplateDelete(){
        try {
            studentDAO.delete(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @After
    public void fun(){
        System.out.println("测试结束!");
    }
}
