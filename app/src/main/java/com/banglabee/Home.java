package com.banglabee;

import android.content.Intent;
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

    int []img = {R.id.img1,R.id.img2,R.id.img3,R.id.img4};
    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/sweet_sensations.ttf");
        TextView myTextView = (TextView)findViewById(R.id.ttl);
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
                if(a < 3){
                    findViewById(img[a++]).clearAnimation();
                    findViewById(img[a]).startAnimation(anim);
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
                Intent intent  = new Intent(Home.this, Study.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.img3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Home.this, Settings.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.img1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Home.this, Play.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.img4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Home.this, Stats.class);
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
}
