package com.henallux.namikot;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.henallux.namikot.Controller.LoginActivity;

import java.io.IOException;

/**
 * Created by Maurine on 8/01/2018.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private SharedPreferences preferences;
    private Context context;

    public MyFirebaseInstanceIdService(Context context){
        super();
        this.context = context;
        this.onTokenRefresh();
    }

    public MyFirebaseInstanceIdService(){

    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("FIREBASE", "Refreshed token: " + refreshedToken);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirebaseToken", refreshedToken);
            editor.commit();
        }
    }
}
