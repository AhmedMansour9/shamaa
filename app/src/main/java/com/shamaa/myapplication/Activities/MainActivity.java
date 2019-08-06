package com.shamaa.myapplication.Activities;

import android.os.Bundle;

import com.shamaa.myapplication.Fragments.Login;
import com.shamaa.myapplication.R;

import am.appwise.components.ni.NoInternetDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    NoInternetDialog noInternetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noInternetDialog = new NoInternetDialog.Builder(this).build();
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new Login()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}
