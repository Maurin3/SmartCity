package com.henallux.namikot.DataAccess;

import com.henallux.namikot.Model.Building;
import com.henallux.namikot.Model.Kot;

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

public class KotDAO {
    public ArrayList<Kot> getAllKots(String urlToGo, String token, ArrayList<Building> buildings) throws Exception {
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
        ArrayList<Kot> kots = this.jsonToKots(jsonStringRead, buildings);
        connection.disconnect();
        return kots;
    }

    public ArrayList<Kot> jsonToKots(String jsonString, ArrayList<Building> buildings) throws Exception{
        ArrayList<Kot> kots = new ArrayList<>();
        Kot kot = null;
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonKot = jsonArray.getJSONObject(i);
            kot = new Kot();
            kot.setId(jsonKot.getInt("id"));
            kot.setSurface(jsonKot.getDouble("surface"));
            kot.setDesignForReducedMobility(jsonKot.getBoolean("isDesignedForReducedMobility"));
            kot.setMonthlyPriceWithAllCharges(jsonKot.getDouble("monthlyPriceWithAllCharges"));
            kot.setMonthlyPriceNoChargesIncluded(jsonKot.getDouble("monthlyPriceNoChargesIncluded"));
            kot.setHasPrivateBathroom(jsonKot.getBoolean("hasPrivateBathroom"));
            kot.setHasPrivateKitchen(jsonKot.getBoolean("hasPrivateKitchen"));
            kot.setHasEquippedKitchen(jsonKot.getBoolean("hasEquippedKitchen"));
            for(Building building : buildings){
                if(jsonKot.getInt("postCode") == building.getPostCode() &&
                        (jsonKot.getString("street")).equals(building.getStreet()) &&
                        (jsonKot.getString("numberOfTheHouse")).equals(building.getNumberOfTheHouse()) &&
                        (jsonKot.getString("cityName")).equals(building.getCityName())
                        ) {
                    kot.setBuilding(building);
                }
            }
            kots.add(kot);
        }
        return kots;
    }
}


