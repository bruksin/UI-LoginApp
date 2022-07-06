package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    private static String snObj;
    private static String nameObj;
    private static String statusObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView infoObjText = findViewById(R.id.imageView);
        statusObj = "";
        SharedPreferences prefs;
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String jwt = prefs.getString("jwt","");
        Log.v("Activity2", "jwt = " + jwt);
        try {
            JWTUtils.decoded(jwt);
        } catch (Exception e) {
            Log.v("JWT", "error = " + e);
        }

        String url = "https://lk.etc-ohrana.ru:8443/api/validate_token.php";
        Map<String, String> params = new HashMap();
        params.put("jwt", jwt);
        params.put("cmd", "GetLastObj");
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            nameObj = response.getJSONObject("obj_info").getString("name");
                            snObj = response.getJSONObject("obj_info").getString("sn");
                            Log.v("response", "snObj = " + snObj);
                            nameObj += "\nдоговор " + response.getJSONObject("obj_info").getString("contract");
                            if (response.getJSONObject("obj_info").getInt("online") != 1) {
                                statusObj += "\nОбъект не на связи!";
                            }
                            else {
                                if (response.getJSONObject("obj_info").getInt("onalarm") == 1) {
                                    statusObj += "\nТревога на объекте!!";
                                }
                            }
                            if (response.getJSONObject("obj_info").getInt("onprotection") == 1) {
                                statusObj += "\nвзят на охрану";
                            }
                            else {
                                statusObj += "\nснят с охраны";
                            }

                            infoObjText.setText(nameObj + statusObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.v("JWT", "error = " + e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("response", "error = " + error);
                    }
                });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    public void onLogout(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //Log.v("BruksLog", "pass=" + intent);
    }

    public void onArm(View view) {
        TextView infoObjText = findViewById(R.id.imageView);
        SharedPreferences prefs;
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String jwt = prefs.getString("jwt","");
        statusObj = "\nсоединение с прибором. ждите...";
        infoObjText.setText(nameObj + statusObj);

        String url = "https://lk.etc-ohrana.ru:8443/api/validate_token.php";
        Map<String, String> params = new HashMap();
        params.put("jwt", jwt);
        params.put("cmd", "ArmObj");
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            statusObj = "\n" + response.getJSONObject("cmdInfo").getString("message");
                            infoObjText.setText(nameObj + statusObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.v("ArmObj", "error = " + e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("response", "error = " + error);
                    }
                });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    public void onDisarm(View view) {
        TextView infoObjText = findViewById(R.id.imageView);
        SharedPreferences prefs;
        prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String jwt = prefs.getString("jwt","");
        statusObj = "\nсоединение с прибором. ждите...";
        infoObjText.setText(nameObj + statusObj);

        String url = "https://lk.etc-ohrana.ru:8443/api/validate_token.php";
        Map<String, String> params = new HashMap();
        params.put("jwt", jwt);
        params.put("cmd", "DisarmObj");
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            statusObj = "\n" + response.getJSONObject("cmdInfo").getString("message");
                            infoObjText.setText(nameObj + statusObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.v("ArmObj", "error = " + e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("response", "error = " + error);
                    }
                });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}