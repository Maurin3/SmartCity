package com.henallux.namikot.Exception;

/**
 * Created by Maurine on 16/12/2017.
 */

public class BadLoginException extends Exception {
    private String login;

    public BadLoginException(String login){
        this.login = login;
    }

    public String getMessage(){
           return this.login;
    }
}
