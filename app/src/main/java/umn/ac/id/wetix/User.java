package umn.ac.id.wetix;

import java.sql.Blob;

public class User {
    private String name;
    private String email;
    private String bday;
    private int balance;
    private Blob image;

//    public User(String name, String email, String bday, int balance, Blob image){
//        this.name = name;
//        this.email = email;
//        this.bday = bday;
//        this.balance = balance;
//        this.image = image;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(byte[] blob) {
    }
}
