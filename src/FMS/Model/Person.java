package Model;

/**
 * Model Class of Person
 */
public class Person {
    private String person_id;
    private String associated_username;
    private String first_name;
    private String last_name;
    private String gender;
    private String father_id;
    private String mother_id;
    private String spouse_id;

    /**
     * Person Class Constructor
     * @param person_id person's id
     * @param associated_username related username
     * @param first_name first name of the person
     * @param last_name last name of the person
     * @param gender gender of the person
     * @param father_id father's id of the person
     * @param mother_id mother's id of the person
     * @param spouse_id spouse's id of the person
     */
    public Person(String person_id, String associated_username, String first_name, String last_name, String gender, String father_id, String mother_id, String spouse_id) {

        this.person_id = person_id;
        this.associated_username = associated_username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.father_id = father_id;
        this.mother_id = mother_id;
        this.spouse_id = spouse_id;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getAssociated_username() {
        return associated_username;
    }

    public void setAssociated_username(String associated_username) {
        this.associated_username = associated_username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather_id() {
        return father_id;
    }

    public void setFather_id(String father_id) {
        this.father_id = father_id;
    }

    public String getMother_id() {
        return mother_id;
    }

    public void setMother_id(String mother_id) {
        this.mother_id = mother_id;
    }

    public String getSpouse_id() {
        return spouse_id;
    }

    public void setSpouse_id(String spouse_id) {
        this.spouse_id = spouse_id;
    }
}
