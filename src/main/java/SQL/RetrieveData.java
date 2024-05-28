package SQL;

import org.example.platformer_game.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrieveData {

    public List<User> getAllUsers1() {
        List<User> userList1 = new ArrayList<>();
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement1 = c.prepareStatement("SELECT username, lvl1score FROM tblusers INNER JOIN tblscore ON tblusers.id = tblscore.scoreId WHERE tblusers.status = 0 ORDER BY lvl1score DESC");
             ResultSet resultSet1 = statement1.executeQuery()) {

            while (resultSet1.next() ) {
                String uname1 = resultSet1.getString("username");
                int lvl1score = resultSet1.getInt("lvl1score");
                User user = new User(uname1, lvl1score);
                userList1.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList1;
    }

    public List<User> getAllUsers2() {
        List<User> userList2 = new ArrayList<>();
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement2 = c.prepareStatement("SELECT username, lvl2score FROM tblusers INNER JOIN tblscore ON tblusers.id = tblscore.scoreId WHERE tblusers.status = 0 ORDER BY lvl2score DESC");
             ResultSet resultSet2 = statement2.executeQuery()) {

            while (resultSet2.next()) {
                String uname2 = resultSet2.getString("username");
                int lvl2score = resultSet2.getInt("lvl2score");
                User user = new User(uname2, lvl2score);
                userList2.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList2;
    }

    public List<User> getAllUsers3() {
        List<User> userList3 = new ArrayList<>();
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement3 = c.prepareStatement("SELECT username, lvl3score FROM tblusers INNER JOIN tblscore ON tblusers.id = tblscore.scoreId WHERE tblusers.status = 0 ORDER BY lvl3score DESC");
             ResultSet resultSet3 = statement3.executeQuery()) {

            while (resultSet3.next()) {
                String uname3 = resultSet3.getString("username");
                int lvl3score = resultSet3.getInt("lvl3score");
                User user = new User(uname3, lvl3score);
                userList3.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList3;
    }
}
