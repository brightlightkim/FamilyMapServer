package Service;

import DataAccess.Database;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import Error.DataAccessException;
import Model.Event;
import Model.Person;
import Result.EventResult;
import Result.EventsResult;

import java.util.ArrayList;
import java.util.Set;

/**
 * Class that performs the Event relating service
 * It can either find one event or every event.
 */
public class EventService {
    /**
     * Return the desired event
     *
     * @return one event
     */
    public EventResult requestEvent(String eventID) throws DataAccessException {
        Database db = new Database();
        try {
            db.openConnection();
            EventResult result;
            Event desiredEvent = new EventDAO(db.getConnection()).find(eventID);
            if (desiredEvent == null) {
                result = new EventResult("Error: No Event that match", false);
                db.closeConnection(false);
                return result;
            } else {
                db.closeConnection(true);
                result = new EventResult(true,
                        desiredEvent.getAssociatedUsername(), desiredEvent.getEventID(),
                        desiredEvent.getPersonID(), desiredEvent.getLatitude(),
                        desiredEvent.getLongitude(), desiredEvent.getCountry(),
                        desiredEvent.getCity(), desiredEvent.getEventType(),
                        desiredEvent.getYear());
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            // Close database connection, ROLLBACK transaction
            db.closeConnection(false);

            // Create and return FAILURE Result object

        }
        return null;
    }

    /**
     * Return every event
     *
     * @return every event
     */
    public EventsResult allEventResult(String userName) throws DataAccessException{
        Database db = new Database();
        try {
            db.openConnection();
            EventsResult result;
            Set<Person> desiredPeople = new PersonDAO(db.getConnection()).findPeople(userName);

            if (desiredPeople == null || desiredPeople.size() == 0){
                result = new EventsResult("Error: no event is available for the given userName", false);
                db.closeConnection(false);
                return result;
            }

            Set<Event> desiredEvents = new EventDAO(db.getConnection()).findAll(desiredPeople, userName);

            return makeAllEventResult(desiredEvents, db);
        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
        }

        return null;
    }

    private EventsResult makeAllEventResult(Set<Event> desiredEvents, Database db) throws DataAccessException{
        try {
            EventsResult result;
            if (desiredEvents == null || desiredEvents.size() == 0) {
                result = new EventsResult("Error: no event is available for the given userName", false);
                db.closeConnection(false);
                return result;
            } else {
                db.closeConnection(true);
                ArrayList<EventResult> eventResultArray = new ArrayList<>();
                for (Event event : desiredEvents) {
                    EventResult eventResult = new EventResult(true,
                            event.getAssociatedUsername(), event.getEventID(),
                            event.getPersonID(), event.getLatitude(),
                            event.getLongitude(), event.getCountry(),
                            event.getCity(), event.getEventType(),
                            event.getYear());
                    eventResultArray.add(eventResult);
                }
                result = new EventsResult(true, eventResultArray);
                return result;
            }
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while making an Array of all events");
        }
    }
}
