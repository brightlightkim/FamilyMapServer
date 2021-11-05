package DataAccess;

import Error.DataAccessException;
import Model.Person;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Data Access Class for Person
 * It stores all persons
 * It finds, inserts, and clears persons.
 */
public class PersonDAO {
    private final Connection conn;
    private boolean spouseCheck; //This is for checking not circling infinitely.
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
        String sql = "SELECT * FROM Persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
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
        String sql = "INSERT INTO Persons (personID, associatedUsername, firstName, " +
                "lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
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

    public void updateSpouseID(Person person, String spouseID) throws DataAccessException{
        String sql = "UPDATE Persons SET spouseID = '" + spouseID + "' WHERE personID = '" + person.getPersonID() + "'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while updating Spouse ID");
        }
    }

    /**
     * find all persons
     *
     * @param associatedUsername ID of the person
     * @return persons array of persons.
     * Associated Username
     */
    public Set<Person> findPeople(String associatedUsername) throws DataAccessException {

        if (associatedUsername != null) {
            Set<Person> persons = new HashSet<>();
            Database db = new Database();
            User user = new UserDAO(db.getConnection()).find(associatedUsername);
            if (user == null){
                db.closeConnection(false);
                return null;
            }
            db.closeConnection(true);
            Person person = find(user.getPersonID());
            if (person != null) {
                spouseCheck = false; // it means not finding the spouse yet.
                persons.addAll(findPeopleByID(person.getPersonID()));
            }
            return persons;
        } else {
            return null;
        }
    }

    private Set<Person> findPeopleByID(String id) throws DataAccessException {
        if (id != null) {
            Set<Person> people = new HashSet<>();
            Person person = find(id);

            if (person != null) {
                people.add(person);
                if (person.getFatherID() != null) {
                    Set<Person> fatherSide = findPeopleByID(person.getFatherID());
                    people.addAll(fatherSide);
                }
                if (person.getMotherID() != null) {
                    Set<Person> motherSide = findPeopleByID(person.getMotherID());
                    people.addAll(motherSide);
                }
                if (person.getSpouseID() != null && !spouseCheck) {
                    spouseCheck = true;
                    Set<Person> spouseSide = findPeopleByID(person.getSpouseID());
                    spouseCheck = false;
                    people.addAll(spouseSide);
                }
            }
            return people;
        } else {
            return null;
        }
    }
}
