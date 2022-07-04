package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SharedPreferences prefs;
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String jwt = prefs.getString("jwt","");
        Log.v("Activity2", "jwt = " + jwt);
        try {
            JWTUtils.decoded(jwt);
        } catch (Exception e) {
            Log.v("JWT", "error = " + e);
        }
    }

    public void onLogout(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //Log.v("BruksLog", "pass=" + intent);
    }
}