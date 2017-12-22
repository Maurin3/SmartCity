package com.henallux.namikot.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                EditText textImputLogin = (EditText) findViewById(R.id.login);
                EditText textImputMail = (EditText) findViewById(R.id.verifMail);

                //connexion à l'API et vérification que le login et mail correspondent -> envoi d'un mail de changement de mdp
                String mail = textImputMail.getText().toString();

                //Toast with "Check your mail"
            }
        });
    }
}
