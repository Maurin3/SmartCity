package com.henallux.namikot.Controller;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.*;
import android.view.*;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.henallux.namikot.DataAccess.BuildingDAO;
import com.henallux.namikot.DataAccess.KotDAO;
import com.henallux.namikot.Model.Building;
import com.henallux.namikot.Model.Kot;
import com.henallux.namikot.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment {

    private SupportMapFragment mSupportMapFragment;
    private SharedPreferences preferences;
    private GoogleMap googleMap;
    private ArrayList<Building> buildings;

    public MapsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        if (!CheckInternetConnection.isDataConnectionAvailable(getContext())){
            Toast.makeText(MapsFragment.this.getContext(), R.string.internetConnection, Toast.LENGTH_SHORT).show();
        }
        else {
            mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(com.henallux.namikot.R.id.mapwhere);
            if (mSupportMapFragment == null) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mSupportMapFragment = SupportMapFragment.newInstance();
                fragmentTransaction.replace(R.id.mapwhere, mSupportMapFragment).commit();
            }

            if (mSupportMapFragment != null) {
                mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {

                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        if (googleMap != null) {
                            preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                            new BuildingTask().execute("http://namikot2.azurewebsites.net/api/Building", preferences.getString("token", null));

                            googleMap.getUiSettings().setAllGesturesEnabled(true);
                            googleMap.getUiSettings().setMapToolbarEnabled(true);
                            googleMap.setMyLocationEnabled(true);

                            LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
                            Criteria criteria = new Criteria();
                            String bestProvider = locationManager.getBestProvider(criteria, true);
                            /*if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            }*/

                            Location location = locationManager.getLastKnownLocation(bestProvider);
                            CameraPosition cameraPosition;
                            if (location != null) {
                                LatLng markerLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                cameraPosition = new CameraPosition.Builder().target(markerLocation).zoom(18.0f).build();
                            } else {
                                LatLng markerNamurGare = new LatLng(50.469335, 4.862534);
                                cameraPosition = new CameraPosition.Builder().target(markerNamurGare).zoom(18.0f).build();
                            }
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                            googleMap.moveCamera(cameraUpdate);

                            setGoogleMap(googleMap);
                        }

                    }
                });
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
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
            MapsFragment.this.buildings = buildings;
            int i = 1;
            new KotTask().execute("http://namikot2.azurewebsites.net/api/Room", preferences.getString("token", null));
            /*for(Building building : buildings) {
                LatLng markerBuilding = new LatLng(building.getLatitude(), building.getLongitude());
                Marker marker = MapsFragment.this.getGoogleMap().addMarker(new MarkerOptions()
                        .position(markerBuilding)
                        .title(getString(R.string.markerDesc) + i)
                        .snippet(building.getNumberOfTheHouse() + ", " + building.getStreet() + " \n "
                                +building.getPostCode() + " " + building.getCityName()));
                i++;
            }*/
        }
    }

    private class KotTask extends AsyncTask<String,Void,ArrayList<Kot>> {
        protected void onPreExecute(){

        }

        protected ArrayList<Kot> doInBackground(String... params){
            KotDAO kotDAO = new KotDAO();
            ArrayList<Kot> kots = new ArrayList<>();
            ArrayList<Building> buildings = MapsFragment.this.buildings;
            try {
                kots = kotDAO.getAllKots(params[0], params[1], buildings);
            }
            catch(Exception e){
                //token = null;
            }
            return kots;
        }

        protected void onPostExecute(ArrayList<Kot> kots){
            for(Kot kot : kots) {
                LatLng markerBuilding = new LatLng(kot.getBuilding().getLatitude(), kot.getBuilding().getLongitude());
                Marker marker = MapsFragment.this.getGoogleMap().addMarker(new MarkerOptions()
                        .position(markerBuilding)
                        .title(getString(R.string.markerDesc) + kot.getId())
                        .snippet(kot.getBuilding().getNumberOfTheHouse() + ", " + kot.getBuilding().getStreet()
                                +kot.getBuilding().getPostCode() + " " + kot.getBuilding().getCityName()));
            }
        }
    }
}