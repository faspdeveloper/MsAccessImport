package cc.altius.fhi.importMsAccess;

import cc.altius.fhi.importMsAccess.service.DbService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImportMsAccessApplication implements CommandLineRunner {

    @Autowired
    private DbService dbService;

    public static void main(String[] args) {
        SpringApplication.run(ImportMsAccessApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String databaseURL = "jdbc:ucanaccess:///home/akil/db/globalmoh.accdb";
        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String sql = "select TABLE_NAME From information_schema.tables where table_schema='PUBLIC'";
            Statement statement = connection.createStatement();
            ResultSet resultTables = statement.executeQuery(sql);
            while (resultTables.next()) {
                String sqlData = "SELECT * FROM " + resultTables.getString(1);
                Statement statementData = connection.createStatement();
                ResultSet resultData = statementData.executeQuery(sqlData);
                ResultSetMetaData rsmdData = resultData.getMetaData();
                if (this.dbService.createTable(resultTables.getString(1), rsmdData)) {
                    System.out.println(resultTables.getString(1) + " table created");
                } else {
                    System.out.println("Error occurred while trying to create table " + resultTables.getString(1));
                }

                List<Map<String, Object>> params = new LinkedList<>();
                while (resultData.next()) {
                    Map<String, Object> data = new HashMap<>();
                    for (int y = 1; y <= rsmdData.getColumnCount(); y++) {
                        data.put(rsmdData.getColumnName(y), resultData.getObject(y));
                    }
                    params.add(data);
                }
                int rows = this.dbService.insertIntoTable(resultTables.getString(1), rsmdData, params);
                System.out.println(rows + " data imported for table " + resultTables.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
