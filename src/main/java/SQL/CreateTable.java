package SQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public static void main(String[] args){
        createTables();
    }
    private static void createTables(){
        try(Connection c = MySqlConnection.getConnection();
            Statement statement = c.createStatement()) {

            String createTableQuery1 = "CREATE TABLE IF NOT EXISTS tblusers (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) NOT NULL, " +
                    "password VARCHAR(50) NOT NULL)";
            statement.execute(createTableQuery1);

            String createTableQuery2 = "CREATE TABLE IF NOT EXISTS tblscore (" +
                    "scoreId INT AUTO_INCREMENT PRIMARY KEY, " +
                    "lvl1score INT(20) NOT NULL, " +
                    "lvl2score INT(20) NOT NULL, " +
                    "lvl3score INT(20) NOT NULL, " +
                    "userId INT(20) NOT NULL, " +
                    "FOREIGN KEY (userId) REFERENCES tblusers(id))";
            statement.execute(createTableQuery2);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
