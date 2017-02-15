package com.example.rezwan.spellingc;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by rezwan on 2/11/17.
 */

public class Congrats extends DialogFragment {

    private View v;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.MyDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_congrats, container, false);
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        v.findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
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

    }
}
