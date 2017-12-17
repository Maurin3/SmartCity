package com.henallux.namikot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import static android.view.View.*;

import com.henallux.namikot.DataAccess.LoginDAO;
import com.henallux.namikot.Exception.BadLoginException;


public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signIn = (Button) findViewById(R.id.signIn);
        TextView forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        TextView signUp = (TextView) findViewById(R.id.signUp);


        signIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText textImputLogin = (EditText) findViewById(R.id.login);
                EditText textImputPassword = (EditText) findViewById(R.id.password);

                String password = textImputPassword.getText().toString();
                String login = textImputLogin.getText().toString();

                new LoginTask().execute("http://namikot2.azurewebsites.net/api/jwt", login, password);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, AccountCreationActivity.class);
                startActivity(intent);
            }
        });
    }


    private class LoginTask extends AsyncTask<String,Void,String> {
        protected void onPreExecute(){
            ProgressBar progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
            progressBarLogin.setVisibility(VISIBLE);
        }

        protected String doInBackground(String... params){
            LoginDAO loginDAO = new LoginDAO();
            String token = "";
            try {
                token = loginDAO.login(params[0], params[1], params[2]);
            }
            catch (BadLoginException badLoginException){
                this.cancel(true);
                return badLoginException.getMessage();
            }
            catch(Exception e){
                token = null;
            }
            return token;
        }

        protected void onPostExecute(String token){
            ProgressBar progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
            progressBarLogin.setVisibility(GONE);
            preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", token);
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        protected void onCancelled(String error){
            ProgressBar progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
            progressBarLogin.setVisibility(GONE);
            Toast.makeText(LoginActivity.this, getString(R.string.badLoginExcept1) + " (" + error +") "+ getString(R.string.badLoginExcept2), Toast.LENGTH_SHORT).show();
        }
    }
}
