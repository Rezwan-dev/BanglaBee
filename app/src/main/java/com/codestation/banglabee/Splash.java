package com.codestation.banglabee;


import android.content.Intent;
import android.os.Handler;


import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class Splash extends AwesomeSplash {

    boolean activityVisible  = true;

    @Override
    public void initSplash(ConfigSplash configSplash) {
        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.accent); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(800); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_TOP); //or Flags.REVEAL_TOP



        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.splash_logo); //or any other drawable
        configSplash.setAnimLogoSplashDuration(800); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

/*

        //Customize Path
        configSplash.setPathSplash(Constants.DROID_LOGO); //set path String
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(3000);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.accent); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(3000);
        configSplash.setPathSplashFillColor(R.color.Wheat); //path object filling color
*/


        //Customize Title
        configSplash.setTitleSplash(getString(R.string.tag));
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(28f); //float value
        configSplash.setAnimTitleDuration(800);
        configSplash.setAnimTitleTechnique(Techniques.SlideInUp);
        //configSplash.setTitleFont("fonts/sweet_sensations.ttf"); //provide string to your font located in assets/fonts/


    }

    @Override
    public void animationsFinished() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(activityVisible) {
                    Intent intent = new Intent(Splash.this, Home.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 800);

    }

    @Override
    protected void onResume() {
        activityVisible = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        activityVisible = false;
        super.onPause();
    }
}
