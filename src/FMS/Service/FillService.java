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



    public FillService() throws FileNotFoundException {
        try {
            Reader reader = new FileReader("locations.json");
            location = (LocationData) gson.fromJson(reader, LocationData.class);
            reader = new FileReader("mnames.json");
            maleNames = (MaleNamesData) gson.fromJson(reader, MaleNamesData.class);
            reader = new FileReader("fnames.json");
            femaleNames = (FemaleNamesData) gson.fromJson(reader, FemaleNamesData.class);
            reader = new FileReader("snames.json");
            surnames = (SurnamesData) gson.fromJson(reader, SurnamesData.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fill it and show the result
     *
     * @return the result of fill
     */

    public FillResult fillResult(String username, int generations) throws DataAccessException {
        String personID = UUID.randomUUID().toString();
        String gender;
        if (getRandomNum(0,1) == 0){
            gender = "MALE";
        }
        else{
            gender = "FEMALE";
        }
        String surname = surnames.getData()[getRandomNum(0, surnames.getData().length)];
        int birthYear = getRandomNum(1921, 2021);
        if (generations < 1){
            throw new DataAccessException("invalid generation");
        }
        else if (generations > 20){
            throw new DataAccessException("Too much generations");
        }
        Person newPerson = generatePerson(username, surname, gender, birthYear, generations);
        //TODO: get the associated usernames array of events and persons.
        //TODO: check when to add it to the server.
        //TODO: Make the array or something.. >> and do continually.

        return null;
    }

    private Person generatePerson(String username, String surname, String gender,
                                  int birthYear, int generations) throws DataAccessException {

        Person father = null;
        Person mother = null;
        Person person = null;
        try {
            if (generations > 1) {
                //at least 13 birth year more than the kid
                int fatherBirthYear = getRandomNum(birthYear - 40, birthYear - 10);
                //this makes mother birth year for the max of 50 years of old.
                int motherBirthYear = getRandomNum(fatherBirthYear - 10, fatherBirthYear + 10);

                String motherSideSurname = surnames.getData()[getRandomNum(0, surnames.getData().length)];

                father = generatePerson(username, surname, "MALE", fatherBirthYear, generations - 1);
                mother = generatePerson(username, motherSideSurname, "FEMALE", motherBirthYear, generations - 1);

                // Set mother's and father's spouse IDs
                father.setSpouseID(mother.getPersonID());
                mother.setSpouseID(father.getPersonID());
                // Add marriage events to mother and father + marriage could happen child's birth or remarriage.
                addMarriageEvent(username, father, mother, getRandomNum(motherBirthYear + 13, motherBirthYear + 50));
                // (their marriage events must be in synch with each other)
            }

            String personID = UUID.randomUUID().toString();

            String personName;

            if (gender.equals("MALE")) {
                personName = maleNames.getData()[getRandomNum(0, maleNames.getData().length)];
            } else {
                personName = femaleNames.getData()[getRandomNum(0, femaleNames.getData().length)];
            }

            // Set person's properties
            if (father != null && mother != null) {
                person = new Person(personID, username, personName, surname, gender,
                        father.getFatherID(), mother.getPersonID(), null);
            } else {
                person = new Person(personID, username, personName, surname, gender,
                        null, null, null);
            }
            // Generate events for person (except marriage)

            Event birth = createBirthEvent(username, personID, birthYear);
            Event death = createDeathEvent(username, personID, birth, birthYear);
            // Save person in database
            Database db = new Database();
            db.openConnection();
            new PersonDAO(db.openConnection()).insert(person);
            new EventDAO(db.openConnection()).insert(birth);
            new EventDAO(db.openConnection()).insert(death);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return person;
    }

    private Event createBirthEvent(String username, String personID, int birthYear){
        Location birthPlace = location.getData()[getRandomNum(0, location.getData().length)];
        String birthEventID = UUID.randomUUID().toString();
        Event birth = new Event(birthEventID, username, personID, birthPlace.getLatitude(),
                birthPlace.getLongitude(), birthPlace.getCountry(),
                birthPlace.getCity(), "BIRTH", birthYear);
        return birth;
    }

    private Event createDeathEvent(String username, String personID, Event birth, int birthYear){
        Location deathPlace = null;
        Location birthPlace = new Location(birth.getLatitude(), birth.getLongitude(), birth.getCity(), birth.getCountry());

        for (Location location : location.getData()){
            if (!location.equals(birthPlace)) {
                if (location.getCountry().equals(birthPlace.getCountry())) {
                    deathPlace = location;
                    break;
                }
            }
        }

        String deathEventID = UUID.randomUUID().toString();
        Event death = new Event(deathEventID, username, personID, deathPlace.getLatitude(),
                deathPlace.getLongitude(), birthPlace.getCountry(),
                deathPlace.getCity(), "BIRTH", birthYear);
        return death;
    }

    private Event createMarriageEvent(String marriageID, String username, String personID, Location location, int year){

        Event marriage = new Event(marriageID, username, personID, location.getLatitude(), location.getLongitude(),
                location.getCountry(), location.getCity(), "MARRIAGE", year);

        return marriage;
    }

    private void addMarriageEvent(String username, Person father, Person mother, int year) {
        String marriageID = UUID.randomUUID().toString();
        Location marriagePlace = location.getData()[getRandomNum(0, location.getData().length)];
        Event marriageForHusband = createMarriageEvent(marriageID, username, father.getPersonID(), marriagePlace, year);
        Event marriageForWife = createMarriageEvent(marriageID, username, mother.getPersonID(), marriagePlace, year);
        try{
            Database db = new Database();
            db.openConnection();
            new EventDAO(db.openConnection()).insert(marriageForHusband);
            new EventDAO(db.openConnection()).insert(marriageForWife);
            db.closeConnection(true);
        }
        catch (DataAccessException e){
            e.printStackTrace();
        }
    }

    private int getRandomNum(int min, int max) {
        int randomAge = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return randomAge;
    }
}
