package ru.domain;

public class User {

    private int id;
    private int addressID;
    private int musicTypeID;
    private String login;
    private String password;
    private int roleID;

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int address) {
        this.addressID = address;
    }

    public int getMusicTypeID() {
        return musicTypeID;
    }

    public void setMusicTypeID(int musicTypeID) {
        this.musicTypeID = musicTypeID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
