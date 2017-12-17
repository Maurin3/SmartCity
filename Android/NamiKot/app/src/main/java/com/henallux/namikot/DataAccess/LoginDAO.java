package com.henallux.namikot.DataAccess;

import com.henallux.namikot.Exception.BadLoginException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Maurine on 15/12/2017.
 */

public class LoginDAO {
    public JSONObject loginToJson(String login, String password)throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserName", login);
        jsonObject.put("Password", password);
        return jsonObject;
    }

    public String jsonToTokens(String jsonString) throws JSONException {
        JSONObject jsonObject= new JSONObject(jsonString);
        return jsonObject.getString("access_token");
    }

    public String login(String urlToGo, String login, String password) throws Exception, BadLoginException {
        URL url = new URL(urlToGo);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-type", "application/json");
        connection.addRequestProperty("Host","namikot2.azurewebsites.net");
        connection.addRequestProperty("Cache-Control", "no-cache");
        String jsonStringWrite = this.loginToJson(login, password).toString();
        OutputStream os = connection.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        connection.connect();
        writer.write(jsonStringWrite);
        writer.flush();
        writer.close();
        if(connection.getResponseCode() == 400 || connection.getResponseCode() == 401){
            throw new BadLoginException(login);
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
        String token = this.jsonToTokens(jsonStringRead);
        connection.disconnect();
        return token;
    }
}
