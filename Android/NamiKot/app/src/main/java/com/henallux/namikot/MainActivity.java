package com.henallux.namikot;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.*;
import android.support.v7.app.AppCompatActivity;

import android.view.*;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    private MapsFragment mapFragment = new MapsFragment();
    //Quand connexion à l'API pour savoir où on est, garder en mémoire l'emplacement (ou géocodage)


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListenerBottom
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            BottomNavigationView navigationTop = (BottomNavigationView) findViewById(R.id.navBar);
            switch (item.getItemId()) {
                case R.id.navigation_kots:
                    navigationTop.setVisibility(VISIBLE);
                    if(navigationTop.getSelectedItemId() == R.id.navigation_list) {
                        ListFragment listFragment = new ListFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content, listFragment)
                                .commit();
                    }
                    else{
                        getMapFragment();
                    }
                    return true;
                case R.id.navigation_messages:
                    navigationTop.setVisibility(GONE);
                    MessagesFragment messagesFragment = new MessagesFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, messagesFragment)
                            .commit();
                    return true;
                case R.id.navigation_profile:
                    navigationTop.setVisibility(GONE);
                    ProfileFragment profileFragment = new ProfileFragment();
                    getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, profileFragment)
                        .commit();
                    return true;
                }
            return false;
        }

    };
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListenerTop
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    ListFragment listFragment = new ListFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, listFragment)
                            .commit();
                    return true;
                case R.id.navigation_map:
                    getMapFragment();
                    return true;
            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationBottom = (BottomNavigationView) findViewById(R.id.navigation);
        navigationBottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListenerBottom);

        BottomNavigationView navigationTop = (BottomNavigationView) findViewById(R.id.navBar);
        navigationTop.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListenerTop);

        getMapFragment();
    }

    private void getMapFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mapFragment)
                .commit();
    }

}
