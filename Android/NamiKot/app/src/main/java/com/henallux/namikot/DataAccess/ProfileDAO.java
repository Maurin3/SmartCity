package com.henallux.namikot.DataAccess;

import com.henallux.namikot.Model.Login;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Maurine on 17/12/2017.
 */

public class ProfileDAO {
    /*
    public ArrayList<Login> getLogin(String urlToGo, String token) throws Exception {
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
        ArrayList<Login> buildings = this.jsonToBuildings(jsonStringRead);
        connection.disconnect();
        return buildings;
    }

    public ArrayList<Login> jsonToBuildings(String jsonString) throws Exception{
        ArrayList<Login> buildings = new ArrayList<>();
        Login building = null;
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonBuilding = jsonArray.getJSONObject(i);
            building = new Login();
            building.setCityName(jsonBuilding.getString("cityName"));
            building.setStreet(jsonBuilding.getString("street"));
            building.setNumberOfTheHouse(jsonBuilding.getString("numberOfTheHouse"));
            building.setFloorRoom(jsonBuilding.getInt("floorRoom"));
            building.setPostCode(jsonBuilding.getInt("postCode"));
            building.setLatitude(jsonBuilding.getDouble("latitude"));
            building.setLongitude(jsonBuilding.getDouble("longitude"));
            buildings.add(building);
        }
        return buildings;
    }
    */
}
