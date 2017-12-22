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
import android.widget.EditText;
import android.widget.Toast;

import com.henallux.namikot.DataAccess.ProfileDAO;
import com.henallux.namikot.DataAccess.UserIdDAO;
import com.henallux.namikot.Exception.UserInfoNotFoundException;
import com.henallux.namikot.Model.User;
import com.henallux.namikot.R;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Maurine on 20/12/2017.
 */

public class ModifyProfileFragment extends Fragment {
    private SharedPreferences preferences;
    private User user;
    private String stringFirstName;
    private String stringLastName;
    private String stringBirthdate;

    private EditText textFirstName;
    private EditText textLastName;
    private EditText textBirthdate;
    private EditText textLogin;
    private EditText textMail;

    public ModifyProfileFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!CheckInternetConnection.isDataConnectionAvailable(getContext())){
            Toast.makeText(ModifyProfileFragment.this.getContext(), R.string.loginPasswordEmpty, Toast.LENGTH_SHORT).show();
        }
        else {
            preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            new UserIdTask().execute("http://namikot2.azurewebsites.net/api/Account/", preferences.getString("token", null), preferences.getString("userName", null));
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modify_profile, container, false);
    }

    private class UserIdTask extends AsyncTask<String,Void,String> {
        protected void onPreExecute(){

        }

        protected String doInBackground(String... params){
            UserIdDAO userIdDAO = new UserIdDAO();
            User user = null;
            try {
                user = userIdDAO.getOneId(params[0], params[1], params[2]);
            }
            catch(MalformedURLException e){
                user = null;
                Toast.makeText(ModifyProfileFragment.this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            catch(Exception e){
                user = null;
            }
            ModifyProfileFragment.this.setUser(user);
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
        protected void onPreExecute() {

        }

        protected User doInBackground(String... params) {
            ProfileDAO profileDAO = new ProfileDAO();
            User user;
            try {
                user = profileDAO.getOneLogin(params[0], params[1], params[2]);
            }
            catch (UserInfoNotFoundException e){
                this.cancel(true);
                return null;
            }
            catch (Exception e) {
                user = null;
            }
            ModifyProfileFragment.this.setUser(user);
            return user;
        }

        protected void onPostExecute(User user) {
            setTextViews();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTextsForTextViews(sdf.format(user.getBirthdate().getTime()), user.getFirstName(),
                        user.getLastName(), user.getLogin(), user.getMail());
            setButtonsListener();
        }

        protected void onCancelled(User user){
            ModifyProfileFragment.this.setTextViews();
            ModifyProfileFragment.this.setTextsForTextViews("??/??/????", "", "", ModifyProfileFragment.this.getUser().getLogin(), ModifyProfileFragment.this.getUser().getMail());
            setButtonsListener();
        }
    }

    private class ModifyUserTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String ...params) {
            String result = null;
            ProfileDAO profileDAO = new ProfileDAO();
            User user =  ModifyProfileFragment.this.getUser();
            String requestMethod;
            if(user.getBirthdate() == null && user.getFirstName() == null && user.getLastName() == null){
                requestMethod = "POST";
            }
            else{
                requestMethod = "PUT";
            }
            user.setFirstName(ModifyProfileFragment.this.stringFirstName);
            user.setLastName(ModifyProfileFragment.this.stringLastName);
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                Date date = sdf.parse(ModifyProfileFragment.this.stringBirthdate);
                gregorianCalendar.setTime(date);
                user.setBirthdate(gregorianCalendar);
                result = profileDAO.modifyUser(params[0], params[1], user, requestMethod);
            }
            catch(ParseException e){
                Toast.makeText(ModifyProfileFragment.this.getContext(), R.string.parseException, Toast.LENGTH_LONG).show();
                this.cancel(true);
                return result;
            }
            catch (Exception e){
                e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            goToProfileFragment();
        }

        @Override
        protected void onCancelled(String result) {
            goToProfileFragment();
        }
    }
    public void setButtonsListener(){
        Button buttonCancel = getActivity().findViewById(R.id.modify_button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfileFragment();
            }
        });

        Button buttonSave = getActivity().findViewById(R.id.modify_button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyProfileFragment.this.stringBirthdate = textBirthdate.getText().toString();
                ModifyProfileFragment.this.stringFirstName = textFirstName.getText().toString();
                ModifyProfileFragment.this.stringLastName = textLastName.getText().toString();
                new ModifyUserTask().execute("http://namikot2.azurewebsites.net/api/UserInfo/", preferences.getString("token", null));
            }
        });
    }
    public void goToProfileFragment(){
        ProfileFragment profileFragment = new ProfileFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, profileFragment)
                .commit();
    }
    public void setTextViews(){
        textFirstName = getActivity().findViewById(R.id.modify_user_first_name);
        textLastName = getActivity().findViewById(R.id.modify_user_last_name);
        textBirthdate = getActivity().findViewById(R.id.modify_user_birthdate);
        textLogin = getActivity().findViewById(R.id.modify_login_user);
        textMail = getActivity().findViewById(R.id.modify_mail_user);
    }
    public  void setTextsForTextViews(String forBirthdate, String forFirstName, String forLastName,
                                      String forLogin, String forMail){
        textBirthdate.setText(forBirthdate);
        textFirstName.setText(forFirstName);
        textLastName.setText(forLastName);
        textLogin.setText(forLogin);
        textMail.setText(forMail);
    }
}
