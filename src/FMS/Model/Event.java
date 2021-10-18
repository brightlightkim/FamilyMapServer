package Model;

import java.util.Objects;

/**
 * Model Class for Event
 */
public class Event {
    private String EventID;
    private String AssociatedUsername;
    private String PersonID;
    private float Latitude;
    private float Longitude;
    private String Country;
    private String City;
    private String EventType;
    private int Year;

    /**
     * Event Class Constructor
     * @param event_id related event id
     * @param associated_username related username
     * @param person_id person's id
     * @param latitude latitude of the event
     * @param longitude longitude of the event
     * @param country country of the event
     * @param city city of the event
     * @param event_type Event type such as marriage of the event
     * @param year year of the event
     */
    public Event(String event_id, String associated_username, String person_id, float latitude, float longitude, String country, String city, String event_type, int year) {
        this.EventID = event_id;
        this.AssociatedUsername = associated_username;
        this.PersonID = person_id;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.Country = country;
        this.City = city;
        this.EventType = event_type;
        this.Year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Float.compare(event.Latitude, Latitude) == 0 && Float.compare(event.Longitude, Longitude) == 0 && Year == event.Year && EventID.equals(event.EventID) && Objects.equals(AssociatedUsername, event.AssociatedUsername) && Objects.equals(PersonID, event.PersonID) && Objects.equals(Country, event.Country) && Objects.equals(City, event.City) && Objects.equals(EventType, event.EventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(EventID, AssociatedUsername, PersonID, Latitude, Longitude, Country, City, EventType, Year);
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        this.EventID = eventID;
    }

    public String getAssociatedUsername() {
        return AssociatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.AssociatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        this.PersonID = personID;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        this.Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        this.Longitude = longitude;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        this.Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        this.EventType = eventType;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        this.Year = year;
    }
}
