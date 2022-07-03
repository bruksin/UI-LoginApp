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

import java.util.ArrayList;

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

        TextView infoText = (TextView) findViewById(R.id.tv_subtitle);
        EditText printLogin = (EditText) findViewById(R.id.et_email);
        EditText printPassword = (EditText) findViewById(R.id.text_password);
        String textLogin = printLogin.getText().toString();
        String textPassword = printPassword.getText().toString();
        Log.v(textLogin, "pass=" + infoText.getText());
        if (textLogin.equals(testLogin) && textPassword.equals(testPassword)) {
            Intent intent = new Intent("com.example.loginapp.MainActivity2");
            startActivity(intent);
        }
        else {
            infoText.setText("Не верный логин или пароль");
        }

    }
}
