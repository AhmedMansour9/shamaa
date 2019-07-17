package com.shamaa.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.shamaa.myapplication.R;
import com.shamaa.myapplication.SharedPrefManager;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView logo =findViewById(R.id.logo);
//        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.animationsplash);
        Animation animationSlideInLeft = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        animationSlideInLeft.setDuration(3000);

        logo.startAnimation(animationSlideInLeft);
        animationSlideInLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                String login= SharedPrefManager.getInstance(getBaseContext()).getUserToken();

                if(login!=null){
                    startActivity(new Intent(Splash.this, TabsLayouts.class));
                    finish();


                }else {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();

                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });


    }
}
