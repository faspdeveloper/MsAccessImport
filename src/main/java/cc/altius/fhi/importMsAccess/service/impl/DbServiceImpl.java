/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.fhi.importMsAccess.service.impl;

import cc.altius.fhi.importMsAccess.dao.DbDao;
import cc.altius.fhi.importMsAccess.service.DbService;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class DbServiceImpl implements DbService {

    @Autowired
    private DbDao dbDao;

    @Override
    public boolean createTable(String tableName, ResultSetMetaData rsmData) throws SQLException {
        return this.dbDao.createTable(tableName, rsmData);
    }

    @Override
    public int insertIntoTable(String tableName, ResultSetMetaData rsmData, List<Map<String, Object>> params) throws SQLException {
        return this.dbDao.insertIntoTable(tableName, rsmData, params);
    }

}
