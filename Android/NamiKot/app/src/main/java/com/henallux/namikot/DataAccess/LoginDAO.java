package com.henallux.namikot.DataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public void creationAccount(String login, String mail, String password)throws Exception{
        URL url = new URL("http://namikot2.azurewebsites.net/api/Account");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.addRequestProperty("Content-Type","application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserName", login);
        jsonObject.put("Password", password);
    }
}
