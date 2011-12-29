package com.alibaba.druid.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import junit.framework.TestCase;
import oracle.jdbc.driver.OraclePreparedStatement;

import com.alibaba.druid.util.JdbcUtils;

public class TestOraclePreparedStatement extends TestCase {

    private String jdbcUrl;
    private String user;
    private String password;

    public void setUp() throws Exception {
        jdbcUrl = "jdbc:oracle:thin:@10.20.149.85:1521:ocnauto";
        // jdbcUrl = "jdbc:oracle:thin:@20.20.149.85:1521:ocnauto"; // error url
        user = "alibaba";
        password = "ccbuauto";
    }

    public void test_0() throws Exception {
        Class.forName(JdbcUtils.getDriverClassName(jdbcUrl));

        Connection conn = DriverManager.getConnection(jdbcUrl, user, password);

        // ResultSet metaRs = conn.getMetaData().getTables(null, "ALIBABA", null, new String[] {"TABLE"});
        // JdbcUtils.printResultSet(metaRs);
        // metaRs.close();

        OraclePreparedStatement oracleStmt = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT ? FROM DUAL");
        oracleStmt = (OraclePreparedStatement) stmt;
        {
            
            stmt.setString(1, "aaa");
            ResultSet rs = stmt.executeQuery();
            rs.next();

            rs.close();
            
            oracleStmt.clearDefines();
        }
        {
            stmt.setString(1, "bbb");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            
            rs.close();
            stmt.close();
        }

        conn.close();
    }
}