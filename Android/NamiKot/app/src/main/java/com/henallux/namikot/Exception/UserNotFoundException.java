package com.henallux.namikot.Exception;

/**
 * Created by Maurine on 8/01/2018.
 */

public class UserNotFoundException extends Exception {
    private String userName;
    private String mail;

    public UserNotFoundException(String userName, String mail){
        this.mail = mail;
        this.userName = userName;
    }

    @Override
    public String getMessage() {
        return userName + ", " + mail;
    }
}
