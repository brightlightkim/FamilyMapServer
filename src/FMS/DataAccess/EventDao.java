package DataAccess;

import Model.Event;
import Exception.DataAccessException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Data Access Class for Event
 * It stores all events
 * It finds, inserts, and clears events.
 */
public class EventDao {
    private ArrayList<Event> events;
    private final Connection conn;

    public EventDao() {
        events = new ArrayList<>();
        conn = null;
    }

    public EventDao(Connection conn)
    {
        events = new ArrayList<>();
        this.conn = conn;
    }
    /**
     * clear the events
     */
    void clear(){}

    /**
     * find the event by id
     * @param username uersname of the event
     * @return return the event
     */
    Event find(String username){return null;}

    /**
     * insert the event
     * @param event the event
     */
    void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEvent_id());
            stmt.setString(2, event.getAssociated_username());
            stmt.setString(3, event.getPerson_id());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEvent_type());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * find the user's all events
     * @param eventID eventID of the events
     * @return return the events.
     */
    ArrayList<Event> findAll(String eventID){

        return null;
    }
}
