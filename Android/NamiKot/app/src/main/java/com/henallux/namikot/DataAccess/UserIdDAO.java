package com.henallux.namikot.DataAccess;

import com.henallux.namikot.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Maurine on 20/12/2017.
 */

public class UserIdDAO {
    public User getOneId(String urlToGo, String token, String userName) throws Exception {
        URL urlPart1 = new URL(urlToGo);
        URL url = new URL(urlPart1, userName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.addRequestProperty("Host","namikot2.azurewebsites.net");
        connection.addRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
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
        User user = this.jsonToId(jsonStringRead);
        connection.disconnect();
        return user;
    }

    public User jsonToId(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        User user = new User();
        user.setId(jsonObject.getString("id"));
        user.setLogin(jsonObject.getString("userName"));
        user.setMail(jsonObject.getString("email"));
        return user;
    }
}
