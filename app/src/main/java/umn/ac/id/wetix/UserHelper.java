package umn.ac.id.wetix;

public class UserHelper {
    String username, email, password, conPassword;

    public UserHelper(){}

    public UserHelper(String username, String email, String password, String conPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.conPassword = conPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConPassword() {
        return conPassword;
    }

    public void setConPassword(String conPassword) {
        this.conPassword = conPassword;
    }
}
