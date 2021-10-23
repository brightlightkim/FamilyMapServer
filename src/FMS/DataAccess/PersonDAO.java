package DataAccess;

import Model.Person;
import Error.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Class for Person
 * It stores all persons
 * It finds, inserts, and clears persons.
 */
public class PersonDAO {
    private final Connection conn;

    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * clear persons
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Persons;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("There is no database at all!");
        }
    }

    /**
     * find the person with given id
     *
     * @param personID ID of the person
     * @return the person
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("FatherID"),
                        rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if (rs != null) {
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
     * insert the person
     *
     * @param person It's the person
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Persons (PersonID, AssociatedUsername, FirstName, " +
                "LastName, Gender, FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting person into the database");
        }
    }

    /**
     * find all persons
     *
     * @param associatedUsername ID of the person
     * @return persons array of persons.
     * Associated Username
     */
    public ArrayList<Person> findPeople(String associatedUsername) throws DataAccessException {

        if (associatedUsername != null) {
            ArrayList<Person> persons = new ArrayList<>();
            Person person = findPersonByUsername(associatedUsername);
            persons.addAll(findPeopleByID(person.getPersonID()));
            return persons;
        } else {
            return null;
        }
    }

    private ArrayList<Person> findPeopleByID(String id) throws DataAccessException {
        if (id != null) {
            ArrayList<Person> people = new ArrayList<>();
            Person person = find(id);
            people.add(person);
            ArrayList<Person> fatherSide = findPeopleByID(person.getFatherID());
            people.addAll(fatherSide);
            ArrayList<Person> motherSide = findPeopleByID(person.getMotherID());
            people.addAll(motherSide);
            ArrayList<Person> spouseSide = findPeopleByID(person.getSpouseID());
            people.addAll(spouseSide);
            return people;
        } else {
            return null;
        }
    }

    private Person findPersonByUsername(String associatedUsername) throws DataAccessException {
        Person person = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("FatherID"),
                        rs.getString("MotherID"), rs.getString("SpouseID"));
            }
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
