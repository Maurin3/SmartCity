package com.henallux.namikot.Controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.henallux.namikot.DataAccess.CreationAccountDAO;
import com.henallux.namikot.Exception.AlreadyExistsException;
import com.henallux.namikot.R;

import static android.view.View.GONE;

public class AccountCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        if(!CheckInternetConnection.isDataConnectionAvailable(getApplicationContext())){
            Toast.makeText(AccountCreationActivity.this, R.string.internetConnection, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AccountCreationActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else{
            Button signUpButton = (Button) findViewById(R.id.signUpButton);
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText textImputLogin = (EditText) findViewById(R.id.signUpLogin);
                    EditText textImputPassword1 = (EditText) findViewById(R.id.signUpPassword1);
                    EditText textImputPassword2 = (EditText) findViewById(R.id.signUpPassword2);
                    EditText textImputMail = (EditText) findViewById(R.id.signUpMail);

                    String password1 = textImputPassword1.getText().toString();
                    String password2 = textImputPassword2.getText().toString();
                    String login = textImputLogin.getText().toString();
                    String mail = textImputMail.getText().toString();
                    /*
                    if(!isMailCorrect(mail)){
                        Toast.makeText(AccountCreationActivity.this, R.string.passwordsNotConcording, Toast.LENGTH_LONG).show();
                    }*/
                    if(!password1.equals(password2)){
                        Toast.makeText(AccountCreationActivity.this, R.string.passwordsNotConcording, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!isPasswordCorrect(password1)){
                            Toast.makeText(AccountCreationActivity.this, R.string.passwordNotCorrect, Toast.LENGTH_LONG).show();
                        }
                        else {
                            new AccountCreationTask().execute("http://namikot2.azurewebsites.net/api/Account", login, mail, password1);
                        }
                    }
                }
            });
        }
    }

    public boolean isMailCorrect(String mail){
        String[] mailSplit1 = mail.split("@");
        if(mailSplit1.length <= 1){
            return false;
        }
        else{
            String[] mailSplit2 = mailSplit1[1].split(".");
            return mailSplit2[0] != null && mailSplit2[1] != null;
        }
    }

    public boolean isPasswordCorrect(String password){
        boolean hasDigit = false;
        boolean hasUpperCaseChar = false;
        int uniquesChars = 0;
        char[] stringAllLettersSeparated = password.toCharArray();
        for(int i = 0; i < password.length(); i++){
            char caracter = password.charAt(i);
            if(Character.isDigit(caracter)){
                hasDigit = Character.isDigit(caracter);
            }
            if(Character.isUpperCase(caracter)){
                hasUpperCaseChar = Character.isUpperCase(caracter);
            }
            boolean isUnique = true;
            for(int j = 0; j < stringAllLettersSeparated.length && isUnique; j++){
                if(i != j) {
                    isUnique = caracter != stringAllLettersSeparated[j];
                }
            }
            if(isUnique){
                uniquesChars++;
            }
        }
        return password.length() >= 8 && hasDigit && hasUpperCaseChar && uniquesChars >= 6;
    }

    private class AccountCreationTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarCreationAccount);
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {
            CreationAccountDAO creationAccountDAO = new CreationAccountDAO();
            String result;
            try {
                result = creationAccountDAO.creationAccount(params[0], params[1], params[2], params[3]);
            }
            catch (AlreadyExistsException e){
                this.cancel(true);
                return e.getMessage();
            }
            catch (Exception e) {
               result = null;
            }
            return result;
        }

        protected void onPostExecute(String number) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarCreationAccount);
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(AccountCreationActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        protected void onCancelled(String error) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarCreationAccount);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AccountCreationActivity.this, getString(R.string.alreadyExistsExceptionPart1) + " (" + error + ") " + getString(R.string.alreadyExistsExceptionPart2), Toast.LENGTH_LONG).show();
        }
    }
}
