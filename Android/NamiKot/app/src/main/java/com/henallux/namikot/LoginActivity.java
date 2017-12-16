package com.henallux.namikot;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.*;
import com.henallux.namikot.DataAccess.LoginDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

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

                //ptetre les mettre dans une classe à part
                String password = textImputPassword.getText().toString();
                String login = textImputLogin.getText().toString();
                Log.i("password","Password : " + password);
                //Attention respecter la découpe en couche
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

        protected String doInBackground(String... params){
            LoginDAO loginDAO = new LoginDAO();
            String token = "";
            //HttpResponse
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-type", "application/json");
                connection.addRequestProperty("Host","namikot2.azurewebsites.net");
                connection.addRequestProperty("Cache-Control", "no-cache");
                String jsonStringWrite = loginDAO.loginToJson(params[1], params[2]).toString();
                OutputStream os = connection.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(os);
                connection.connect();
                writer.write(jsonStringWrite);
                writer.flush();
                writer.close();
                String contentType = connection.getContentType();

                String response = connection.getContent().toString();
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
                token = loginDAO.jsonToTokens(jsonStringRead);
                connection.disconnect();
            }
            catch(Exception e){
                token = null;
            }
            return token;
        }
        @Override
        protected void onPostExecute(String token){
            Log.i("token", token);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
