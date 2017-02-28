package com.codestation.banglabee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.io.FileOutputStream;

import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by rezwan on 2/11/17.
 */

public class Congrats extends DialogFragment {



    private View v;
    private static String score;
    private View snap;
    private boolean initialized;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.MyDialog);
    }
    public static Congrats getInstance(String score){
        Congrats.score = score;
        return new Congrats();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_congrats, container, false);
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!initialized) {
            init();
        }
    }

    public void init(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView imageView  = (ImageView) v.findViewById(R.id.afterExplode);
                ((ExplosionField)v.findViewById(R.id.explosion)).explode(v.findViewById(R.id.makeExplode0), imageView);
                ((ExplosionField)v.findViewById(R.id.explosion)).explode(v.findViewById(R.id.makeExplode1), imageView);
                ((ExplosionField)v.findViewById(R.id.explosion)).explode(v.findViewById(R.id.makeExplode2), imageView);
                ((ExplosionField)v.findViewById(R.id.explosion)).explode(v.findViewById(R.id.makeExplode3), imageView);
                ((ExplosionField)v.findViewById(R.id.explosion)).explode(v.findViewById(R.id.makeExplode4), imageView);
                ((ExplosionField)v.findViewById(R.id.explosion)).explode(v.findViewById(R.id.makeExplode5), imageView);
                ((ExplosionField)v.findViewById(R.id.explosion)).explode(v.findViewById(R.id.makeExplode6), imageView);
                ((ExplosionField)v.findViewById(R.id.explosion)).explode(v.findViewById(R.id.makeExplode7), imageView);
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reveal();
            }
        },1000);

    }

    private void reveal() {
        v.findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        final SharedPreferences sharedPref = getActivity().getSharedPreferences("spellingC",Context.MODE_PRIVATE);
        ((TextView)v.findViewById(R.id.name_tv_congrats)).setText(sharedPref.getString("name", "Player One"));
        ((TextView)v.findViewById(R.id.scoreTv)).setText(score);
        String filePath = sharedPref.getString("saved", "");
        RoundedImage profile_pic = (RoundedImage) v.findViewById(R.id.profile_pic);
        if (filePath.length() > 0) {
            File imgFile = new File(filePath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                profile_pic.setImageBitmap(myBitmap);

            }
        }
        snap  = v.findViewById(R.id.snap);
        v.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snap.setDrawingCacheEnabled(true);
                snap.buildDrawingCache();
                Bitmap bm = snap.getDrawingCache();
                shareBitmap(bm,"share");
            }
        });
        Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/sweet_sensations.ttf");
        TextView myTextView = (TextView)v.findViewById(R.id.ttlc);
        myTextView.setTypeface(myTypeface);
        View reveal = v.findViewById(R.id.reveal);
        reveal.setVisibility(View.VISIBLE);
        reveal.animate().alpha(1.0f).setDuration(600);
        initialized = true;

    }

    private void shareBitmap (Bitmap bitmap,String fileName) {
        try {
            File file = new File(getContext().getCacheDir(), fileName + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(     android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
