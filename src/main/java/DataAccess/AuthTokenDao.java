package DataAccess;

import Model.AuthToken;
import Model.Event;

import java.util.ArrayList;

/**
 * Data Access Class for AuthToken Model Class.
 * It's used to access the data of AuthToken.
 * It finds, inserts, and clears the authorized token.
 */
public class AuthTokenDao {
    private ArrayList<AuthToken> authTokens;

    public AuthTokenDao() {
    }

    /**
     * Clear the authorized tokens.
     */
    void clear(){}

    /**
     * Find the AuthToken class
     * @param token token to look up to
     * @return the AuthToken class of the matching token key.
     */
    AuthToken find(String token){return null;}

    /**
     * insert the AuthToken.
     * @param token insert the token.
     */
    void insert(AuthToken token){}

}
