package com.luoyupiaoshang.dao;

import com.luoyupiaoshang.domain.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: ligang
 * date: 2018/1/24
 * time: 10:52
 */
public interface StudentDAO {
    /**
     * 查询所有学生
     * @return Student 返回的是一个学生列表
     */
    List<Student> query() throws SQLException;

    /**
     * 增加一个学生
     * @param stundet 待添加的学生
     */
    void save(Student stundet);

    /**
     * 删除一个学生
     * @param id 待删除的学生id
     */
    void delete(int id);
}
