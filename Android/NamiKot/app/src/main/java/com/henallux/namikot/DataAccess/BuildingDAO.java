package com.henallux.namikot.DataAccess;

import com.henallux.namikot.Exception.BadLoginException;
import com.henallux.namikot.Model.Building;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Maurine on 16/12/2017.
 */

public class BuildingDAO {

    public ArrayList<Building> getAllBuildings(String urlToGo, String token) throws Exception {
        URL url = new URL(urlToGo);
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
        ArrayList<Building> buildings = this.jsonToBuildings(jsonStringRead);
        connection.disconnect();
        return buildings;
    }

    public ArrayList<Building> jsonToBuildings(String jsonString) throws Exception{
        ArrayList<Building> buildings = new ArrayList<>();
        Building building = null;
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonBuilding = jsonArray.getJSONObject(i);
            building = new Building();
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
}
