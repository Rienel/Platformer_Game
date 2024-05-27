package SQL;

import org.example.platformer_game.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrieveData {

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT username, lvl1score, lvl2score, lvl3score FROM tblusers INNER JOIN tblscore ON tblusers.id = tblscore.scoreId");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String uname = resultSet.getString("username");
                int lvl1score = resultSet.getInt("lvl1score");
                int lvl2score = resultSet.getInt("lvl2score");
                int lvl3score = resultSet.getInt("lvl3score");
                User user = new User(uname, lvl1score, lvl2score, lvl3score);
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
