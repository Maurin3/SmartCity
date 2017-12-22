package com.henallux.namikot.DataAccess;

import com.henallux.namikot.Exception.AlreadyExistsException;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Maurine on 16/12/2017.
 */

public class CreationAccountDAO {

    public JSONObject accountToJson(String login, String mail, String password)throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserName", login);
        jsonObject.put("Password", password);
        jsonObject.put("Email", mail);
        return jsonObject;
    }

    public String creationAccount(String urlToGo, String login, String mail, String password)throws Exception{
        String result;
        URL url = new URL(urlToGo);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.addRequestProperty("Content-Type","application/json");
        connection.addRequestProperty("Host","namikot2.azurewebsites.net");
        connection.addRequestProperty("Cache-Control", "no-cache");
        String jsonStringWrite = this.accountToJson(login, mail, password).toString();
        OutputStream os = connection.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        connection.connect();
        writer.write(jsonStringWrite);
        writer.flush();
        writer.close();
        if (connection.getResponseCode() == 400){       //400 : existe déjà
            throw new AlreadyExistsException(login, mail);
        }
        result = connection.getResponseMessage();
        connection.disconnect();
        return result;
    }

}
