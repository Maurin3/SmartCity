package com.henallux.namikot.Model;

/**
 * Created by Maurine on 17/12/2017.
 */

public class Login {
    private String login;
    private String mail;
    private String token;

    public String getLogin() {
        return login;
    }

    public String getMail() {
        return mail;
    }

    public String getToken() {
        return token;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
