package com.henallux.namikot.Controller;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.namikot.DataAccess.ProfileDAO;
import com.henallux.namikot.DataAccess.UserIdDAO;
import com.henallux.namikot.Exception.UserInfoNotFoundException;
import com.henallux.namikot.Model.User;
import com.henallux.namikot.R;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private SharedPreferences preferences;
    private User user;

    private TextView textViewFirstName;
    private TextView textViewLastName;
    private TextView textViewBirthdate;
    private TextView textViewUserName;
    private TextView textViewMail;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(!CheckInternetConnection.isDataConnectionAvailable(getContext())){
            Toast.makeText(ProfileFragment.this.getContext(), R.string.internetConnection, Toast.LENGTH_SHORT).show();
        }
        else{
            preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            new UserIdTask().execute("http://namikot2.azurewebsites.net/api/Account/", preferences.getString("token", null),  preferences.getString("userName", null));
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private class UserIdTask extends AsyncTask<String,Void,String> {
        protected void onPreExecute(){

        }

        protected String doInBackground(String... params){
            UserIdDAO userIdDAO = new UserIdDAO();
            User user;
            try {
                user = userIdDAO.getOneId(params[0], params[1], params[2]);
            }
            catch(MalformedURLException e){
                user = null;
                Toast.makeText(ProfileFragment.this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT);
            }
            catch(Exception e){
                user = null;
            }
            ProfileFragment.this.setUser(user);
            return user.getId();
        }

        protected void onPostExecute(String id){
            new UserTask().execute("http://namikot2.azurewebsites.net/api/UserInfo/", preferences.getString("token", null), id);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    private class UserTask extends AsyncTask<String,Void,User> {
        protected void onPreExecute(){

        }

        protected User doInBackground(String... params){
            ProfileDAO profileDAO = new ProfileDAO();
            User user;
            try {
                user = profileDAO.getOneLogin(params[0], params[1], params[2]);
            }
            catch (UserInfoNotFoundException e){
                this.cancel(true);
                return null;
            }
            catch(Exception e){
                user = null;
            }
            return user;
        }

        protected void onPostExecute(User user){
            ProfileFragment.this.setTextViews();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            ProfileFragment.this.setTextsForTextViews(user.getFirstName(), user.getLastName(),
                    sdf.format(user.getBirthdate().getTime()), user.getLogin(), user.getMail());

            setButtonListener();
        }

        protected void onCancelled(User user){
            ProfileFragment.this.setTextViews();
            ProfileFragment.this.setTextsForTextViews("", "", "??/??/????", ProfileFragment.this.getUser().getLogin(), ProfileFragment.this.getUser().getMail());

            setButtonListener();
        }

        public void setButtonListener(){
            Button button = getActivity().findViewById(R.id.modifyProfileButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModifyProfileFragment modifyProfileFragment = new ModifyProfileFragment();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, modifyProfileFragment)
                            .commit();
                }
            });
        }

    }

    public void setTextViews (){
        textViewFirstName = getActivity().findViewById(R.id.user_first_name);
        textViewLastName = getActivity().findViewById(R.id.user_last_name);
        textViewBirthdate = getActivity().findViewById(R.id.user_birthdate);
        textViewUserName =  getActivity().findViewById(R.id.login_user);
        textViewMail = getActivity().findViewById(R.id.mail_user);
    }

    public void setTextsForTextViews(String forFirstName, String forLastName, String forBirthdate,
                                        String forLogin, String forMail){
        textViewBirthdate.setText(forBirthdate);
        textViewFirstName.setText(forFirstName);
        textViewLastName.setText(forLastName);
        textViewUserName.setText(forLogin);
        textViewMail.setText(forMail);
    }
}
