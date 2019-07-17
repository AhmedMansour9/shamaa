package com.shamaa.myapplication.Activities;

import android.os.Bundle;

import com.shamaa.myapplication.Fragments.Login;
import com.shamaa.myapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new Login()).commit();
    }
}
