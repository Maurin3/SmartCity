package com.henallux.namikot.DataAccess;

import com.henallux.namikot.Model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.GregorianCalendar;

/**
 * Created by Maurine on 17/12/2017.
 */

public class ProfileDAO {

    public User getOneLogin(String urlToGo, String token) throws Exception {
        URL url = new URL(urlToGo);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.addRequestProperty("Host","namikot2.azurewebsites.net");
        connection.addRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        int co = connection.getResponseCode();
        if(connection.getResponseCode() == 400 || connection.getResponseCode() == 401){

        }
        InputStream is = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        String jsonStringRead ="",line;
        while((line=br.readLine())!=null){
            stringBuilder.append(line);
        }
        br.close();
        jsonStringRead = stringBuilder.toString();
        User user = this.jsonToOneLogin(jsonStringRead);
        connection.disconnect();
        return user;
    }

    public User jsonToOneLogin(String jsonString) throws Exception{
        User user = new User();
        JSONObject jsonLogin = new JSONObject(jsonString);
        user.setLogin(jsonLogin.getJSONObject("idAspNetUserNavigation").getString("userName"));
        user.setMail(jsonLogin.getJSONObject("idAspNetUserNavigation").getString("mail"));
        user.setBirthdate((GregorianCalendar)jsonLogin.get("Birthdate"));
        user.setFirstName(jsonLogin.getString("firstName"));
        user.setLastName(jsonLogin.getString("lastName"));
        return user;
    }

}
