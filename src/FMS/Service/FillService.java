package Service;

import Error.DataAccessException;
import JsonData.FemaleNamesData;
import JsonData.LocationData;
import JsonData.MaleNamesData;
import JsonData.SurnamesData;
import Model.Event;
import Model.Person;
import Result.FillResult;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Class that help fill.
 */
public class FillService {
    /**
     * Fill it and show the result
     * @return the result of fill
     */
    Gson gson = new Gson();
    private LocationData location;
    private MaleNamesData maleNames;
    private FemaleNamesData femaleNamesData;
    private SurnamesData surnamesData;

    public FillService() throws FileNotFoundException {
        try {
            Reader reader = new FileReader("locations.json");
            location = (LocationData) gson.fromJson(reader, LocationData.class);
            reader = new FileReader("mnames.json");
            maleNames = (MaleNamesData) gson.fromJson(reader, MaleNamesData.class);
            reader = new FileReader("fnames.json");
            femaleNamesData = (FemaleNamesData) gson.fromJson(reader, FemaleNamesData.class);
            reader = new FileReader("snames.json");
            surnamesData = (SurnamesData) gson.fromJson(reader, SurnamesData.class);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public FillResult fillResult (String username, int generations) throws DataAccessException{

        return null;
    }

    private Person generatePerson(String surname, String gender, int birthYear, int generations) {

        Person father = null;
        Person mother = null;

        if (generations > 1) {
            //at least 13 birth year more than the kid
            int fatherBirthYear = getRandomNum(birthYear-40, birthYear-10);
            //this makes mother birth year for the max of 50 years of old.
            int motherBirthYear = getRandomNum(fatherBirthYear-10, fatherBirthYear+10);

            String fatherSurname = surnamesData[getRandomNum(0,)]
            father = generatePerson("MALE", generations - 1);
            mother = generatePerson("FEMALE", generations - 1);

            //birth, marriage, and death should be there

            // Set mother's and father's spouse IDs

            // Add marriage events to mother and father

            // (their marriage events must be in synch with each other)
        }

        Person person = new Person();
        // Set person's properties

        // Generate events for person (except marriage)
        Event event = new Event()
        // Save person in database

        return person;
    }

    private int getRandomNum(int min, int max){
        int randomAge = (int)Math.floor(Math.random()*(max-min+1)+min);
        return randomAge;
    }
}
