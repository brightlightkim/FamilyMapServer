package Model;

/**
 * Model Class of Person
 */
public class Person {
    private String PersonID;
    private String AssociatedUsername;
    private String FirstName;
    private String LastName;
    private String Gender;
    private String FatherID;
    private String MotherID;
    private String SpouseID;

    /**
     * Person Class Constructor
     * @param person_id person's id
     * @param associated_username related username
     * @param first_name first name of the person
     * @param last_name last name of the person
     * @param gender gender of the person
     * @param FatherID father's id of the person
     * @param mother_id mother's id of the person
     * @param spouse_id spouse's id of the person
     */
    public Person(String person_id, String associated_username, String first_name, String last_name, String gender, String FatherID, String mother_id, String spouse_id) {

        this.PersonID = person_id;
        this.AssociatedUsername = associated_username;
        this.FirstName = first_name;
        this.LastName = last_name;
        this.Gender = gender;
        this.FatherID = FatherID;
        this.MotherID = mother_id;
        this.SpouseID = spouse_id;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        this.PersonID = personID;
    }

    public String getAssociatedUsername() {
        return AssociatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.AssociatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public String getFatherID() {
        return FatherID;
    }

    public void setFatherID(String fatherID) {
        this.FatherID = fatherID;
    }

    public String getMotherID() {
        return MotherID;
    }

    public void setMotherID(String motherID) {
        this.MotherID = motherID;
    }

    public String getSpouseID() {
        return SpouseID;
    }

    public void setSpouseID(String spouseID) {
        this.SpouseID = spouseID;
    }
}
