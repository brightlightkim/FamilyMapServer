package DataAccess;

import Model.AuthToken;
import Exception.DataAccessException;
import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Class for AuthToken Model Class.
 * It's used to access the data of AuthToken.
 * It has autoTokens that store all tokens.
 * It finds, inserts, and clears the authorized token.
 */
public class AuthTokenDAO {
    private final Connection conn;

    public AuthTokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Clear the authorized tokens.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM AuthTokens;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("There is no database at all!");
        }
    }

    /**
     * Find the AuthToken class
     * @param token token to look up to
     * @return the AuthToken class of the matching token key.
     */
    public AuthToken find(String token) throws DataAccessException{
        AuthToken tokenThatMatch;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE AuthToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                tokenThatMatch = new AuthToken(rs.getString("AuthToken"), rs.getString("Username"));
                return tokenThatMatch;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding the token");
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
     * insert the AuthToken.
     * @param token insert the token.
     */
    public void insert(AuthToken token) throws DataAccessException{
        String sql = "INSERT INTO AuthTokens (AuthToken, Username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token.getAuthToken());
            stmt.setString(2, token.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting person into the database");
        }
    }

}
