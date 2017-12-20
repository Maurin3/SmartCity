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

import com.henallux.namikot.DataAccess.KotDAO;
import com.henallux.namikot.DataAccess.ProfileDAO;
import com.henallux.namikot.DataAccess.UserIdDAO;
import com.henallux.namikot.Model.Building;
import com.henallux.namikot.Model.Kot;
import com.henallux.namikot.Model.User;
import com.henallux.namikot.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private SharedPreferences preferences;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        new UserIdTask().execute("http//namikot2.azurewebsites.net/api/Account/" + preferences.getString("userName", null), preferences.getString("token", null));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private class UserIdTask extends AsyncTask<String,Void,String> {
        protected void onPreExecute(){

        }

        protected String doInBackground(String... params){
            UserIdDAO userIdDAO = new UserIdDAO();
            String id = null;
            try {
                id = userIdDAO.getOneId(params[0], params[1]);
            }
            catch(Exception e){
                //token = null;
            }
            return id;
        }

        protected void onPostExecute(String id){
            new UserTask().execute("http//namikot2.azurewebsites.net/api/UserInfo/" + id, preferences.getString("token", null));
        }
    }


    private class UserTask extends AsyncTask<String,Void,User> {
        protected void onPreExecute(){

        }

        protected User doInBackground(String... params){
            ProfileDAO profileDAO = new ProfileDAO();
            User user = null;
            try {
                user = profileDAO.getOneLogin(params[0], params[1]);
            }
            catch(Exception e){
                //token = null;
            }
            return user;
        }

        protected void onPostExecute(User user){

        }
    }

}
