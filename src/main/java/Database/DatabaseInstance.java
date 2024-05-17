package Database;

import java.sql.*;

public class DatabaseInstance {
    private static DatabaseInstance instance;
    Connection conn;

    private DatabaseInstance(String url, String username, String password){
        try {
            conn = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static DatabaseInstance getInstance(String url, String username, String password){
        if(instance == null){
            instance = new DatabaseInstance(url,username,password);
        }  return instance;
    }

    public Connection getConn() {
        return conn;
    }
}
