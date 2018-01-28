package com.luoyupiaoshang.dao;

import com.luoyupiaoshang.domain.Student;
import com.luoyupiaoshang.util.JdbcUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: ligang
 * date: 2018/1/24
 * time: 22:06
 */
public class StudentDAOSpringTemplateImpl implements StudentDAO {
    private JdbcTemplate jdbcTemplate;
    final List<Student> list = new ArrayList<>();

    @Override
    public List<Student> query() throws SQLException {

        String sql = "select id,name,age from student";
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String name = rs.getString("name");

                Student student = new Student();
                student.setId(id);
                student.setAge(age);
                student.setName(name);
                list.add(student);
            }
        });
        return list;
    }

    @Override
    public void save(Student student) {
        String sql = "insert into student(age,name) values (?,?)";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, student.getAge());
                ps.setString(2, student.getName());
            }
        });
    }

    @Override
    public void delete(int id) {
        String sql = "delete from student where id =" + id;
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                 ps.executeUpdate();
            }
        });
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
