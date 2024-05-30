package SQL;

import java.sql.*;

public class MySqlConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/dbplatformers";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin123";
    public static Connection getConnection() {
        Connection c = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database!");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static void main(String[] args) {
        getConnection();
    }
}