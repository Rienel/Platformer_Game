package SQL;

import javafx.event.ActionEvent;

import java.sql.*;

public class InsertScore {

    int score;
    int userId;
    int curlvl;

    public InsertScore(int userId, int score, int curlvl) {
        this.score = score;
        this.userId = userId;
        this.curlvl = curlvl;
    }
    public void insertScore() {

        switch(curlvl){
            case 1:
                try (Connection c = MySqlConnection.getConnection();
                     PreparedStatement selectStatement = c.prepareStatement(
                             "SELECT * FROM tblscore WHERE userId = ?");
                     PreparedStatement statement = c.prepareStatement(
                             "INSERT INTO tblscore (userId, lvl1score, lvl2score, lvl3score) VALUES (?, ?, ?, ?)");
                     PreparedStatement updateStatement = c.prepareStatement(
                        "UPDATE tblscore SET lvl1score = ? WHERE userId = ?")) {

                    selectStatement.setInt(1, userId);
                        try(ResultSet resultSet = selectStatement.executeQuery()){
                            if(!resultSet.next()){
                                statement.setInt(1, userId);
                                statement.setInt(2, score);
                                statement.setInt(3, 0);
                                statement.setInt(4, 0);
                                int rowsInserted = statement.executeUpdate();
                                if (rowsInserted > 0) {
                                    System.out.println("Score for Level 1 Inserted Successfully!");
                                }
                            }else{
                                updateStatement.setInt(1, score);
                                updateStatement.setInt(2, userId);
                                int rowsUpdated = updateStatement.executeUpdate();
                                if (rowsUpdated > 0) {
                                    System.out.println("Score for Level 1 Updated Successfully!");
                                }
                            }
                        }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                curlvl = 0;
                break;
            case 2:
                try (Connection c = MySqlConnection.getConnection();
                     PreparedStatement selectStatement = c.prepareStatement(
                             "SELECT * FROM tblscore WHERE userId = ?");
                     PreparedStatement statement = c.prepareStatement(
                             "INSERT INTO tblscore (userId, lvl1score, lvl2score, lvl3score) VALUES (?, ?, ?, ?)");
                     PreparedStatement updateStatement = c.prepareStatement(
                             "UPDATE tblscore SET lvl2score = ? WHERE userId = ?")) {

                    selectStatement.setInt(1, userId);
                    try(ResultSet resultSet = selectStatement.executeQuery()){
                        if(!resultSet.next()){
                            statement.setInt(1, userId);
                            statement.setInt(2, 0);
                            statement.setInt(3, score);
                            statement.setInt(4, 0);
                            int rowsInserted = statement.executeUpdate();
                            if (rowsInserted > 0) {
                                System.out.println("Score for Level 2 Inserted Successfully!");
                            }
                        }else{
                            updateStatement.setInt(1, score);
                            updateStatement.setInt(2, userId);
                            int rowsUpdated = updateStatement.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("Score for Level 2 Updated Successfully!");
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                curlvl = 0;
                break;
            case 3:
                try (Connection c = MySqlConnection.getConnection();
                     PreparedStatement selectStatement = c.prepareStatement(
                             "SELECT * FROM tblscore WHERE userId = ?");
                     PreparedStatement statement = c.prepareStatement(
                             "INSERT INTO tblscore (userId, lvl1score, lvl2score, lvl3score) VALUES (?, ?, ?, ?)");
                     PreparedStatement updateStatement = c.prepareStatement(
                             "UPDATE tblscore SET lvl3score = ? WHERE userId = ?")) {

                    selectStatement.setInt(1, userId);
                    try(ResultSet resultSet = selectStatement.executeQuery()){
                        if(!resultSet.next()){
                            statement.setInt(1, userId);
                            statement.setInt(2, 0);
                            statement.setInt(3, 0);
                            statement.setInt(4, score);
                            int rowsInserted = statement.executeUpdate();
                            if (rowsInserted > 0) {
                                System.out.println("Score for Level 3 Inserted Successfully!");
                            }
                        }else{
                            updateStatement.setInt(1, score);
                            updateStatement.setInt(2, userId);
                            int rowsUpdated = updateStatement.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("Score for Level 3 Updated Successfully!");
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                curlvl = 0;
                break;
        }

    }

}
