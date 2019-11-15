/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.fhi.importMsAccess.dao.impl;

import cc.altius.fhi.importMsAccess.dao.DbDao;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author akil
 */
@Repository
public class DbDaoImpl implements DbDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean createTable(String tableName, ResultSetMetaData rsmData) throws SQLException {
        String sql = "CREATE TABLE `" + tableName + "` ( ";
        for (int x = 1; x <= rsmData.getColumnCount(); x++) {
            sql += "`"
                    + rsmData.getColumnName(x)
                    + "` "
                    + getDataTypeName(rsmData.getColumnTypeName(x), rsmData.getColumnDisplaySize(x))
                    + ", ";
        }
        if (rsmData.getColumnCount() > 0) {
            sql = sql.substring(0, sql.length() - 2);
        }
        sql += ");";
        return (this.jdbcTemplate.update(sql) == 0);
    }

    public String getDataTypeName(String dataType, int length) {
        String ans = "";
        switch (dataType) {
            case "VARCHAR":
                if (length > 65535) {
                    ans = "TEXT(" + length + ")";
                } else {
                    ans = "VARCHAR(" + length + ")";
                }
                break;
            case "DOUBLE":
                ans = "DOUBLE";
                break;
            case "INTEGER":
                ans = "INTEGER";
                break;
            case "TIMESTAMP":
                ans = "DATETIME";
                break;
            case "BOOLEAN":
                ans = "BOOLEAN";
                break;
            case "SMALLINT":
                ans = "SMALLINT";
                break;
            default:
                ans = "UNKNOWN";
                break;
        }
        return ans;
    }

    @Override
    public int insertIntoTable(String tableName, ResultSetMetaData rsmData, List<Map<String, Object>> params) throws SQLException {
        String sql = "INSERT INTO `" + tableName + "` (";
        for (int x = 1; x <= rsmData.getColumnCount(); x++) {
            sql += "`" + rsmData.getColumnName(x) + "`,";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ") VALUES(";
        for (int x = 1; x <= rsmData.getColumnCount(); x++) {
            sql += "?, ";
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += ")";
        int count = 0;
        for (Map<String, Object> p : params) {
            Object[] b = new Object[rsmData.getColumnCount()];
            for (int x = 1; x <= rsmData.getColumnCount(); x++) {
                b[x - 1] = p.get(rsmData.getColumnName(x));
            }
            count += this.jdbcTemplate.update(sql, b);
        }
        return count;
    }

}
