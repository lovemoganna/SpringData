package com.luoyupiaoshang.jdbcdaoimpltest;

import com.luoyupiaoshang.dao.StudentDAOImpl;
import com.luoyupiaoshang.domain.Student;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: ligang
 * date: 2018/1/24
 * time: 16:23
 */
public class JdbcDAOImplTest {
    @Test
    public void testStudentQuery() throws SQLException {
        StudentDAOImpl studentDAO = new StudentDAOImpl();
        List<Student> query = studentDAO.query();
        for (Student student : query) {
            System.out.println("ID:"+student.getId()+" "+"AGE:"+student.getAge()+" "+"NAME:"+student.getName());
        }

    }
    @Test
    public void testStudentInsert()  {
        StudentDAOImpl studentDAO = new StudentDAOImpl();

        try {
            Student student= new Student();
            student.setAge(25);
            student.setName("小dongxi");
            studentDAO.save(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStudentDelete()  {
        StudentDAOImpl studentDAO = new StudentDAOImpl();
        try {
            studentDAO.delete(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
