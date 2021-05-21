package umn.ac.id.wetix;

public class UserHelper {
    String name, email, bday;

    public UserHelper(){}

    public UserHelper(String name, String bday, String email) {
        this.name = name;
        this.email = email;
        this.bday = bday;;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    }
