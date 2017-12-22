package com.henallux.namikot.Exception;

/**
 * Created by Maurine on 21/12/2017.
 */

public class AlreadyExistsException extends Exception {
    private String login;
    private String mail;

    public AlreadyExistsException(String login, String mail){
        this.login = login;
        this.mail = mail;
    }

    @Override
    public String getMessage() {
        return login + ", " + mail;
    }
}
