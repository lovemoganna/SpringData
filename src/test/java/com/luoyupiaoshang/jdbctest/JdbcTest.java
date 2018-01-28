package com.luoyupiaoshang.jdbctest;

import com.luoyupiaoshang.util.JdbcUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author: ligang
 * date: 2018/1/22
 * time: 15:56
 */
public class JdbcTest {
    @Test
    public void testGetconnection() throws Exception {
        Connection connection = JdbcUtil.getConnection();
        Assert.assertNotNull(connection);
    }
}
