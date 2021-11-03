package Service;

import DataAccess.PersonDAO;
import Error.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.Event;
import Model.Person;
import Result.AllEventResult;
import Result.EventResult;

import java.util.ArrayList;

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
            Event desiredEvent = new EventDAO(db.openConnection()).find(eventID);
            if (desiredEvent == null) {
                result = new EventResult("No Event that match", false);
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
    public AllEventResult allEventResult(String userName) throws DataAccessException{
        Database db = new Database();
        try {
            db.openConnection();
            ArrayList<Person> desiredPeople = new PersonDAO(db.openConnection()).findPeople(userName);
            ArrayList<Event> desiredEvents = new EventDAO(db.openConnection()).findAll(desiredPeople, userName);
            AllEventResult result;
            if (desiredEvents == null){
                result = new AllEventResult("no event is available for the given userName", false);
                db.closeConnection(false);
                return result;
            }
            else{
                db.closeConnection(true);
                ArrayList<EventResult> eventResultArray = new ArrayList<>();
                for (Event event : desiredEvents){
                    EventResult eventResult = new EventResult(true,
                            event.getAssociatedUsername(), event.getEventID(),
                            event.getPersonID(), event.getLatitude(),
                            event.getLongitude(), event.getCountry(),
                            event.getCity(), event.getEventType(),
                            event.getYear());
                    eventResultArray.add(eventResult);
                }
                result = new AllEventResult(true, eventResultArray);
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
        }

        return null;
    }
}
