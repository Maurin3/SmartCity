package com.henallux.namikot.DataAccess;

import com.henallux.namikot.Exception.UserInfoNotFoundException;
import com.henallux.namikot.Model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Maurine on 17/12/2017.
 */

public class ProfileDAO {

    public User getOneLogin(String urlToGo, String token, String id) throws Exception {
        URL urlPart1 = new URL(urlToGo);
        URL url = new URL(urlPart1, id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.addRequestProperty("Host","namikot2.azurewebsites.net");
        connection.addRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        if(connection.getResponseCode() == 404){
            throw new UserInfoNotFoundException();
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
        User user = this.jsonToOneLogin(jsonStringRead, id);
        connection.disconnect();
        return user;
    }

    public User jsonToOneLogin(String jsonString, String id) throws Exception{
        User user = new User();
        JSONObject jsonLogin = new JSONObject(jsonString);
        user.setLogin(jsonLogin.getJSONObject("idAspNetUserNavigation").getString("userName"));
        user.setMail(jsonLogin.getJSONObject("idAspNetUserNavigation").getString("email"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        Date date = sdf.parse(jsonLogin.getString("birthdate"));
        gregorianCalendar.setTime(date);
        user.setBirthdate(gregorianCalendar);
        user.setFirstName(jsonLogin.getString("firstName"));
        user.setLastName(jsonLogin.getString("lastName"));
        user.setId(id);
        return user;
    }

    public String modifyUser(String urlToGo, String token, User user, String method) throws Exception {
        URL urlPart1 = new URL(urlToGo);
        URL url;
        if(method.equals("PUT")){
            url = new URL(urlPart1, user.getId());
        }
        else{
            url = urlPart1;
        }
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-type", "application/json");
        connection.setRequestProperty("Host","namikot2.azurewebsites.net");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Cache-Control", "no-cache");
        String jsonStringWrite = this.userToJson(user).toString();
        OutputStream os = connection.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        connection.connect();
        writer.write(jsonStringWrite);
        writer.flush();
        writer.close();
        int co = connection.getResponseCode();
        if(connection.getResponseCode() == 400 || connection.getResponseCode() == 401){

        }
        connection.disconnect();
        return "ok";
    }

    public JSONObject userToJson(User user) throws Exception{
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("IdAspNetUser", user.getId());
        jsonUser.put("FirstName", user.getFirstName());
        jsonUser.put("LastName", user.getLastName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String date = sdf.format(user.getBirthdate().getTime());
        jsonUser.put("Birthdate", date);
        return jsonUser;
    }

}
