package com.henallux.namikot.DataAccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Maurine on 20/12/2017.
 */

public class UserIdDAO {
    public String getOneId(String urlToGo, String token) throws Exception {
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
        String id = this.jsonToId(jsonStringRead);
        connection.disconnect();
        return id;
    }

    public String jsonToId(String jsonString) throws JSONException {
        JSONObject jsonObject= new JSONObject(jsonString);
        return jsonObject.getString("id");
    }
}
