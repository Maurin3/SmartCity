package com.henallux.namikot.Controller;

import android.app.Notification;
import android.content.Intent;
import android.net.MailTo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.henallux.namikot.DataAccess.CreationAccountDAO;
import com.henallux.namikot.DataAccess.ForgotPasswordDAO;
import com.henallux.namikot.Exception.AlreadyExistsException;
import com.henallux.namikot.Exception.UserNotFoundException;
import com.henallux.namikot.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textImputLogin = (EditText) findViewById(R.id.verifLogin);
                EditText textImputMail = (EditText) findViewById(R.id.verifMail);
                EditText textImputPassword1 = (EditText) findViewById(R.id.editPassword);
                EditText textImputPassword2 = (EditText) findViewById(R.id.editPassword2);

                String mail = textImputMail.getText().toString();
                String userName = textImputLogin.getText().toString();
                String password1 = textImputPassword1.getText().toString();
                String password2 = textImputPassword2.getText().toString();

                //Toast with "Check your mail"
                if(!Utils.isDataConnectionAvailable(getApplicationContext())){
                    Toast.makeText(ForgotPasswordActivity.this, R.string.internetConnection, Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!Utils.validate(mail)){
                        Toast.makeText(ForgotPasswordActivity.this, R.string.mailNotValid, Toast.LENGTH_LONG).show();
                    }
                    if(!password1.equals(password2)){
                        Toast.makeText(ForgotPasswordActivity.this, R.string.passwordsNotConcording, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!isPasswordCorrect(password1)){
                            Toast.makeText(ForgotPasswordActivity.this, R.string.passwordNotCorrect, Toast.LENGTH_LONG).show();
                        }
                        else {
                            new ForgotPasswordTask().execute("http://namikot2.azurewebsites.net/api/Account/", userName, mail, password1);
                        }
                    }
                }
            }
        });
    }

    public boolean isPasswordCorrect(String password){
        boolean hasDigit = false;
        boolean hasUpperCaseChar = false;
        int uniquesChars = 0;
        char[] stringAllLettersSeparated = password.toCharArray();
        for(int i = 0; i < password.length(); i++){
            char character = password.charAt(i);
            if(Character.isDigit(character)){
                hasDigit = Character.isDigit(character);
            }
            if(Character.isUpperCase(character)){
                hasUpperCaseChar = Character.isUpperCase(character);
            }
            boolean isUnique = true;
            for(int j = 0; j < stringAllLettersSeparated.length && isUnique; j++){
                if(i != j) {
                    isUnique = character != stringAllLettersSeparated[j];
                }
            }
            if(isUnique){
                uniquesChars++;
            }
        }
        return password.length() >= 8 && hasDigit && hasUpperCaseChar && uniquesChars >= 6;
    }

    private class ForgotPasswordTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarForgotPassword);
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {
            ForgotPasswordDAO forgotPasswordDAO = new ForgotPasswordDAO();
            String result;
            try {
                result = forgotPasswordDAO.passwordChange(params[0], params[1], params[2], params[3]);
            }
            catch (AlreadyExistsException e) {
                this.cancel(true);
                return e.getMessage();
            }
            catch (UserNotFoundException e) {
                this.cancel(true);
                return e.getMessage();
            }
            catch (Exception e) {
                result = null;
            }
            return result;
        }

        protected void onPostExecute(String code) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarForgotPassword);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.passwordChanged), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        protected void onCancelled(String error) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarCreationAccount);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.alreadyExistsExceptionPart1) + " (" + error + ") " + getString(R.string.alreadyExistsExceptionPart2), Toast.LENGTH_LONG).show();
        }
    }
}
