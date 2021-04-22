package umn.ac.id.wetix;

public class UserHelper {
    String username, email, bday, picture, password, conPassword;

    public UserHelper(){}

    public UserHelper(String username, String bday, String email, String picture, String password) {
        this.username = username;
        this.email = email;
        this.bday = bday;
        this.password = password;
        this.picture = picture;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getPhoto() {
        return picture;
    }

    public void setPhoto(String picture) {
        this.picture = picture;
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
