package com.codestation.banglabee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;


import dyanamitechetan.vusikview.VusikView;

public class Home extends AppCompatActivity {

    int[] img = {R.id.img1, R.id.img2, R.id.img3, R.id.img4};
    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/sweet_sensations.ttf");
        TextView myTextView = (TextView) findViewById(R.id.ttl);
        myTextView.setTypeface(myTypeface);
        final Animation anim = new ScaleAnimation(
                0f, 1f, // Start and end values for the X axis scaling
                0f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true);// Needed to keep the result of the animation
        anim.setDuration(350);
        anim.setInterpolator(new OvershootInterpolator());
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                findViewById(img[a]).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (a < 3) {
                    findViewById(img[a++]).clearAnimation();
                    findViewById(img[a]).startAnimation(anim);
                } else {
                    check();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.img1).startAnimation(anim);
        VusikView vusikView = (VusikView) findViewById(R.id.vusik);
        vusikView.start();
        findViewById(R.id.img2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Study.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.img3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Settings.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.img1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Play.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.img4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Stats.class);
                startActivity(intent);
            }
        });
       /* findViewById(R.id.testFrag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Congrats newFragment = new  Congrats();
                newFragment.show(getSupportFragmentManager(), "dialog");
            }
        });*/

    }

    private void check() {
        SharedPreferences sharedPref = getSharedPreferences("spellingC", Context.MODE_PRIVATE);
        if (sharedPref.getBoolean("firstLoad", true)) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("firstLoad", false);
            editor.apply();

            AlertDialog alertDialog = new AlertDialog.Builder(Home.this).create();
            alertDialog.setTitle("Requirement");
            alertDialog.setMessage("This application requires Bangla Keyboard. Please switch to Unicode Bangla Keyboard or Please download one from PlayStore (eg. ridmik keyboard)");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.BLACK);

        }
    }
}
