/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.fhi.importMsAccess.service;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akil
 */
public interface DbService {

    public boolean createTable(String tableName, ResultSetMetaData rsmData) throws SQLException;

    public int insertIntoTable(String tableName, ResultSetMetaData rsmData, List<Map<String, Object>> params) throws SQLException;
}
