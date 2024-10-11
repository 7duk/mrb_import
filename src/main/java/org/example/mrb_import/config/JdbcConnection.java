package org.example.mrb_import.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcConnection {

    public static Connection getConnection() {
        Connection connection = null;
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            // Thay thế các thông tin kết nối với thông tin của bạn
//            String url = "jdbc:mysql://103.221.221.104:3306/rjpmozpk_map"; // URL đến cơ sở dữ liệu Oracle
//            String username = "rjpmozpk"; // Tên người dùng
//            String password = "1d3iGn2c8H"; // Mật khẩu

            Class.forName("com.mysql.cj.jdbc.Driver");
            // Thay thế các thông tin kết nối với thông tin của bạn
            String url = "jdbc:mysql://localhost:3306/mrb"; // URL đến cơ sở dữ liệu Oracle
            String username = "root"; // Tên người dùng
            String password = "duc11052002dac!"; // Mật khẩu

            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        } catch (SQLException ex) {
            System.out.println("Error: unable to connect to the database!");
            ex.printStackTrace();
        }
        return connection;
    }
}
