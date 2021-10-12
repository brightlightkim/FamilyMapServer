package DataAccess;

import Model.*;
import Exception.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        String sql = "INSERT INTO Events (Username, Password, Email, FirstName, LastName, " +
                "Gender, PersonID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(1, user.getPassword());
            stmt.setString(1, user.getEmail());
            stmt.setString(1, user.getFirstName());
            stmt.setString(1, user.getLastName());
            stmt.setString(1, user.getGender());
            stmt.setString(1, user.getPersonID());

        } catch (SQLException e){
            throw new DataAccessException("Error encountered while inserting user into the database");
        }
    }

    /**
     * find the user by id
     * @param id id of the user
     * @return user
     */
    public User find(String id){return null;}

    /**
     * clear users
     */
    public void clear(){}

}
