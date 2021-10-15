package Model;

import java.util.Objects;

public class User {
    private String Username;
    private String Password;
    private String Email;
    private String FirstName;
    private String LastName;
    private String Gender;
    private String PersonID;

    /**
     * User's Constructor
     * @param username username of user
     * @param password password of user
     * @param email email of user
     * @param first_name first name of user
     * @param last_name last name of user
     * @param gender gender of user
     * @param person_id person's id of user
     */
    public User(String username, String password, String email, String first_name, String last_name, String gender, String person_id) {
        this.Username = username;
        this.Password = password;
        this.Email = email;
        this.FirstName = first_name;
        this.LastName = last_name;
        this.Gender = gender;
        this.PersonID = person_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(Username, user.Username) && Objects.equals(Password, user.Password) && Objects.equals(Email, user.Email) && Objects.equals(FirstName, user.FirstName) && Objects.equals(LastName, user.LastName) && Objects.equals(Gender, user.Gender) && Objects.equals(PersonID, user.PersonID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Username, Password, Email, FirstName, LastName, Gender, PersonID);
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
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

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        this.PersonID = personID;
    }
}
