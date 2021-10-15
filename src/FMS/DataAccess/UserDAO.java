package DataAccess;

import Model.*;
import Exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Class for user
 * It stores all users
 * It finds, inserts, and clears users.
 */
public class UserDAO {
    private final Connection conn;

    public UserDAO(Connection conn){
        this.conn = conn;
    }
    /**
     * inser the user
     * @param user user
     */
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO Users (Username, Password, Email, FirstName, LastName, " +
                "Gender, PersonID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting user into the database");
        }
    }

    /**
     * find the user by username
     * @param username username of the user
     * @return user
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("FirstName"),
                        rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("PersonID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * clear users
     */
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Users");
            stmt.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("There is no database at all!");
        }
    }

}
