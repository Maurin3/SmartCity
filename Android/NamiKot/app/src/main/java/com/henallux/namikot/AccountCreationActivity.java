package com.henallux.namikot;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.henallux.namikot.DataAccess.CreationAccountDAO;
import com.henallux.namikot.DataAccess.LoginDAO;
import com.henallux.namikot.Exception.BadLoginException;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AccountCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

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

                if(!password1.equals(password2)){
                    Toast.makeText(AccountCreationActivity.this, "Some text", Toast.LENGTH_SHORT);
                }
                else {
                    new AccountCreationTask().execute("http://namikot2.azurewebsites.net/api/Account", login, mail, password1);
                }
            }
        });
    }

    private class AccountCreationTask extends AsyncTask<String,Void,String> {

        protected String doInBackground(String... params) {
            CreationAccountDAO creationAccountDAO = new CreationAccountDAO();
            //HttpResponse
            try {
                creationAccountDAO.creationAccount(params[0], params[1], params[2], params[3]);
            } catch (Exception e) {
               String token = null;
            }
            return "0";
        }

        protected void onPostExecute(String number) {
            ProgressBar progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
            progressBarLogin.setVisibility(GONE);
            Intent intent = new Intent(AccountCreationActivity.this, MainActivity.class);
            startActivity(intent);
        }
        /*
        protected void onCancelled(String error) {
            ProgressBar progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
            progressBarLogin.setVisibility(GONE);
            Toast.makeText(AccountCreationActivity.this, getString(R.string.badLoginExcept1) + " (" + error + ") " + getString(R.string.badLoginExcept2), Toast.LENGTH_SHORT).show();
        }*/
    }
}
