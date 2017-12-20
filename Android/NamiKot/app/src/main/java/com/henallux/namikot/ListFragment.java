package com.henallux.namikot;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.henallux.namikot.DataAccess.BuildingDAO;
import com.henallux.namikot.DataAccess.KotDAO;
import com.henallux.namikot.Model.Building;
import com.henallux.namikot.Model.Kot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    private ListView listView;
    private SharedPreferences preferences;
    private ArrayList<Building> buildings;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        new BuildingTask().execute("http://namikot2.azurewebsites.net/api/Building", preferences.getString("token", null));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    private class BuildingTask extends AsyncTask<String,Void,ArrayList<Building>> {
        protected void onPreExecute(){

        }

        protected ArrayList<Building> doInBackground(String... params){
            BuildingDAO buildingDAO = new BuildingDAO();
            ArrayList<Building> buildings = new ArrayList<>();
            try {
                buildings = buildingDAO.getAllBuildings(params[0], params[1]);
            }
            catch(Exception e){
                //token = null;
            }
            return buildings;
        }

        protected void onPostExecute(ArrayList<Building> buildings){
            new KotTask().execute("http://namikot2.azurewebsites.net/api/Room", preferences.getString("token", null));
            ListFragment.this.buildings = buildings;
        }
    }

    private class KotTask extends AsyncTask<String,Void,ArrayList<Kot>> {
        protected void onPreExecute(){

        }

        protected ArrayList<Kot> doInBackground(String... params){
            KotDAO kotDAO = new KotDAO();
            ArrayList<Kot> kots = new ArrayList<>();
            ArrayList<Building> buildings = ListFragment.this.buildings;
            try {
                kots = kotDAO.getAllKots(params[0], params[1], buildings);
            }
            catch(Exception e){
                //token = null;
            }
            return kots;
        }

        protected void onPostExecute(ArrayList<Kot> kots){

            listView = getActivity().findViewById(R.id.list);
            ArrayAdapter<Kot> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_expandable_list_item_1, kots);
            listView.setAdapter(adapter);
        }
    }
}
