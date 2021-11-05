package Service;

import DataAccess.Database;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import Error.DataAccessException;
import JsonData.*;
import Model.Event;
import Model.Person;
import Result.FillResult;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Random;
import java.util.UUID;

/**
 * Class that help fill.
 */
public class FillService {

    Gson gson = new Gson();
    private LocationData location;
    private MaleNamesData maleNames;
    private FemaleNamesData femaleNames;
    private SurnamesData surnames;
    private int createdPeopleNum;
    private int createdEventNum;


    public FillService() throws FileNotFoundException {
        try {
            Reader reader = new FileReader("C:\\Users\\brightlightkim\\IdeaProjects\\FamilyMapServerStudent-master\\json\\locations.json");
            location = gson.fromJson(reader, LocationData.class);
            reader = new FileReader("C:\\Users\\brightlightkim\\IdeaProjects\\FamilyMapServerStudent-master\\json\\mnames.json");
            maleNames = gson.fromJson(reader, MaleNamesData.class);
            reader = new FileReader("C:\\Users\\brightlightkim\\IdeaProjects\\FamilyMapServerStudent-master\\json\\fnames.json");
            femaleNames = gson.fromJson(reader, FemaleNamesData.class);
            reader = new FileReader("C:\\Users\\brightlightkim\\IdeaProjects\\FamilyMapServerStudent-master\\json\\snames.json");
            surnames = gson.fromJson(reader, SurnamesData.class);
            createdPeopleNum = 0;
            createdEventNum = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fill it and show the result
     *
     * @return the result of fill
     */

    public FillResult fillResult(String username, String userSurname, int generations) throws DataAccessException {
        String gender;
        String surname = userSurname;
        String firstName;
        if (getRandomNum(0, 1) == 0) {
            gender = "MALE";
            firstName = maleNames.getData()[getRandomNum(0, maleNames.getData().length - 1)];
        } else {
            gender = "FEMALE";
            firstName = femaleNames.getData()[getRandomNum(0, femaleNames.getData().length - 1)];
        }

        if (userSurname == null) {
            surname = surnames.getData()[getRandomNum(0, surnames.getData().length - 1)];
        }
        int birthYear = getRandomNum(1921, 2021);
        if (generations < 1) {
            return new FillResult("Error: Invalid generation number", false);
        } else if (generations > 20) {
            return new FillResult("Error: generation numbers are too great", false);
        }
        generatePerson(username, firstName, surname, gender, birthYear, generations);
        String message = "Successfully added " + createdPeopleNum + " persons and "
                + createdEventNum + " events to the database";

        return new FillResult(message, true);
    }


    public Person generatePerson(String username, String firstName, String surname, String gender,
                                  int birthYear, int generations) throws DataAccessException {

        Person father = null;
        Person mother = null;
        if (generations >= 1) {
            int fatherBirthYear = getRandomNum(birthYear - 40, birthYear - 17);
            int motherBirthYear = getRandomNum(fatherBirthYear - 2, fatherBirthYear+2);
            String fatherFirstName = maleNames.getData()[getRandomNum(0, maleNames.getData().length - 1)];
            String motherFirstName = femaleNames.getData()[getRandomNum(0, femaleNames.getData().length - 1)];
            String motherSideSurname = surnames.getData()[getRandomNum(0, surnames.getData().length - 1)];

            father = generatePerson(username, fatherFirstName, surname, "MALE", fatherBirthYear, generations - 1);
            mother = generatePerson(username, motherFirstName, motherSideSurname, "FEMALE", motherBirthYear, generations - 1);

            updateSpouseIDs(father, mother);
            addMarriageEvent(username, father, mother, getRandomNum(motherBirthYear + 18, motherBirthYear + 50));
        }

        Person person = createPerson(father, mother, username, firstName, surname, gender);
        Event birth = createBirthEvent(username, person.getPersonID(), birthYear);
        Event death = createDeathEvent(username, person.getPersonID(), birth, birthYear);

        addThemToDatabase(person, birth, death);

        return person;
    }

    private void addThemToDatabase(Person person, Event birth, Event death) throws DataAccessException{
        Database db = new Database();
        try {
            db.getConnection();
            new PersonDAO(db.getConnection()).insert(person);
            new EventDAO(db.getConnection()).insert(birth);
            new EventDAO(db.getConnection()).insert(death);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    private Person createPerson(Person father, Person mother, String username, String firstName, String surname, String gender){
        Person person;
        String personID = UUID.randomUUID().toString();
        if (father != null && mother != null) {
            person = new Person(personID, username, firstName, surname, gender,
                    father.getPersonID(), mother.getPersonID(), null);
        } else {
            person = new Person(personID, username, firstName, surname, gender,
                    null, null, null);
        }
        createdPeopleNum++;
        return person;
    }

    private Event createBirthEvent(String username, String personID, int birthYear) {
        Location birthPlace = location.getData()[getRandomNum(0, location.getData().length-1)];
        String birthEventID = UUID.randomUUID().toString();
        Event birth = new Event(birthEventID, username, personID, birthPlace.getLatitude(),
                birthPlace.getLongitude(), birthPlace.getCountry(),
                birthPlace.getCity(), "BIRTH", birthYear);
        createdEventNum++;
        return birth;
    }

    private Event createDeathEvent(String username, String personID, Event birth, int birthYear) {
        Location deathPlace = null;
        Location birthPlace = new Location(birth.getLatitude(), birth.getLongitude(), birth.getCity(), birth.getCountry());

        for (Location location : location.getData()) {
            if (location.getCountry().equals(birthPlace.getCountry())) {
                deathPlace = location;
                break;
            }
        }

        String deathEventID = UUID.randomUUID().toString();
        int deathYear = birthYear + getRandomNum(20, 50);
        if (deathYear >= 2020){
            getRandomNum(birthYear, 2019);
        }
        Event death = new Event(deathEventID, username, personID, deathPlace.getLatitude(),
                deathPlace.getLongitude(), birthPlace.getCountry(),
                deathPlace.getCity(), "DEATH", deathYear);
        createdEventNum++;
        return death;
    }

    private Event createMarriageEvent(String marriageID, String username, String personID, Location location, int year) {

        Event marriage = new Event(marriageID, username, personID, location.getLatitude(), location.getLongitude(),
                location.getCountry(), location.getCity(), "MARRIAGE", year);
        createdEventNum++;
        return marriage;
    }

    private void updateSpouseIDs(Person father, Person mother) throws DataAccessException{
        try {
            Database db = new Database();
            new PersonDAO(db.getConnection()).updateSpouseID(father, mother.getPersonID());
            new PersonDAO(db.getConnection()).updateSpouseID(mother, father.getPersonID());
            db.closeConnection(true);
        }
        catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Error while updating spouse IDs");
        }
    }

    private void addMarriageEvent(String username, Person father, Person mother, int year) throws DataAccessException {
        String marriageIDHusband = UUID.randomUUID().toString();
        Location marriagePlace = location.getData()[getRandomNum(0, location.getData().length - 1)];
        Event marriageForHusband = createMarriageEvent(marriageIDHusband, username, father.getPersonID(), marriagePlace, year);
        String marriageIDWife = UUID.randomUUID().toString();
        Event marriageForWife = createMarriageEvent(marriageIDWife, username, mother.getPersonID(), marriagePlace, year);
        Database db = new Database();
        try {
            db.getConnection();
            new EventDAO(db.getConnection()).insert(marriageForHusband);
            new EventDAO(db.getConnection()).insert(marriageForWife);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
    }

    public int getRandomNum(int min, int max) {
        if (min > max){
            min = max-3;
        }
        Random rand = new Random();
        int randomAge = rand.nextInt((max - min) + 1) + min;
        return randomAge;
    }
}
