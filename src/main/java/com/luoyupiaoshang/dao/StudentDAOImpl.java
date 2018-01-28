package com.luoyupiaoshang.dao;

import com.luoyupiaoshang.domain.Student;
import com.luoyupiaoshang.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: ligang
 * date: 2018/1/24
 * time: 10:55
 * StudentDAO实现类,通过原始的JDBC的方式操作
 */
public class StudentDAOImpl implements StudentDAO {
    List<Student> list = new ArrayList<>();

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @Override
    public List<Student> query() {
        String sql = "select id,name,age from student";
        try {
            connection = JdbcUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            Student student = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int age = resultSet.getInt("age");
                String name = resultSet.getString("name");

                student = new Student();
                student.setId(id);
                student.setAge(age);
                student.setName(name);
                list.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(resultSet, preparedStatement, connection);
        }

        return list;
    }

    @Override
    public void save(Student student) {

        String sql = "insert into student(age,name) values (?,?)";

        //执行插入操作并不需要返回结果集

        try {
            //1.获得连接
            connection = JdbcUtil.getConnection();
            //2. 获得事务
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student.getAge());
            preparedStatement.setString(2, student.getName());
            //3.执行事务-更新
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(resultSet, preparedStatement, connection);
        }

    }

    @Override
    public void delete(int id) {
        String sql = "delete from student where id =" + id;
        //执行插入操作并不需要返回结果集
        try {
            //1.获得连接
            connection = JdbcUtil.getConnection();
            //2. 获得事务
            preparedStatement = connection.prepareStatement(sql);

            //3.执行事务-更新
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(resultSet, preparedStatement, connection);
        }
    }

}
