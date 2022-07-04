package com.example.loginapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);

        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragmet(new LoginFragment());
        pagerAdapter.addFragmet(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);
    }

    static class AuthenticationPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public AuthenticationPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragmet(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }

    public void onLogin(View view) {
        final String testLogin = "demo";
        final String testPassword = "demo";

        TextView infoText = findViewById(R.id.tv_subtitle);
        EditText printLogin = findViewById(R.id.et_email);
        EditText printPassword = findViewById(R.id.text_password);
        String textLogin = printLogin.getText().toString();
        String textPassword = printPassword.getText().toString();
        //Log.v(textLogin, "pass=" + infoText.getText());
        String url = "https://lk.etc-ohrana.ru:8443/api/login.php";
        Map<String, String> params = new HashMap();
        params.put("login", textLogin);
        params.put("passwd", textPassword);

        JSONObject parameters = new JSONObject(params);
        Log.v("JSONObject", " = " + parameters);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("response", "good = " + response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("response", "error = " + error);

                    }
                });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
//        if (textLogin.equals(testLogin) && textPassword.equals(testPassword)) {
//            Intent intent = new Intent(this, MainActivity2.class);;
//            startActivity(intent);
//        }
//        else {
//            infoText.setText("Не верный логин или пароль");
//        }

    }
}
