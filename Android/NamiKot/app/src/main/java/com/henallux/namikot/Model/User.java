package com.henallux.namikot.Model;

import java.util.GregorianCalendar;

/**
 * Created by Maurine on 20/12/2017.
 */

public class User {
    private String login;
    private String mail;
    private String firstName;
    private String lastName;
    private GregorianCalendar birthdate;
    private String id;

    public String getLogin() {
        return login;
    }

    public String getMail() {
        return mail;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public GregorianCalendar getBirthdate() {
        return birthdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBirthdate(GregorianCalendar birthdate) {
        this.birthdate = birthdate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
