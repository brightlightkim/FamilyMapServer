package Model;

public class User {
    private String username;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String gender;
    private String person_id;

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
        this.username = username;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.person_id = person_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }
}
