package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaseDao {
    String URL;
    public BaseDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            URL = "jdbc:mysql://localhost:3306/MemoDB?serverTimezone=Asia/Shanghai";
        } catch (ClassNotFoundException e) {
            System.out.println("cannot find driver\n");
            e.printStackTrace();
        }
    }
    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, "root","123456");
    }
}
