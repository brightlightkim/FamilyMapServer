package DataAccess;

import Model.Event;
import Model.Person;

import java.util.ArrayList;
import java.util.List;

public class EventDao {
    private ArrayList<Event> events;

    public EventDao() {
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
     * @param event
     */
    void insert(Event event){}

    /**
     * find the user's all events
     * @param username username of the events
     * @return return the events.
     */
    ArrayList<Event> findUserEvents(String username){
        return null;
    }
}
