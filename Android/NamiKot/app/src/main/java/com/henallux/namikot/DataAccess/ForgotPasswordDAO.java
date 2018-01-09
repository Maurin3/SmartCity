package com.henallux.namikot.DataAccess;

import com.henallux.namikot.Exception.*;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Maurine on 8/01/2018.
 */

public class ForgotPasswordDAO {
    public JSONObject accountToJson(String login, String mail, String password)throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserName", login);
        jsonObject.put("Password", password);
        jsonObject.put("Email", mail);
        return jsonObject;
    }

    public String passwordChange(String urlToGo, String userName, String mail, String password)throws Exception{
        URL urlPart1 = new URL(urlToGo);
        URL url = new URL(urlPart1, userName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.addRequestProperty("Content-Type","application/json");
        connection.addRequestProperty("Host","namikot2.azurewebsites.net");
        connection.addRequestProperty("Cache-Control", "no-cache");
        String jsonStringWrite = this.accountToJson(userName, mail, password).toString();
        OutputStream os = connection.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        connection.connect();
        writer.write(jsonStringWrite);
        writer.flush();
        writer.close();
        if(connection.getResponseCode() == 404){
            throw new UserNotFoundException(userName, mail);
        }
        String result = connection.getResponseMessage();
        connection.disconnect();
        return result;
    }
}
