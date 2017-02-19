package com.banglabee.stack;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.banglabee.R;
import com.banglabee.mashroor.databasemanagement.WordModel;

import java.util.ArrayList;

/**
 * Created by rezwan on 2/5/17.
 */

public class SwipeStackAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private ArrayList<WordModel> mData;
    private Context context;

    public SwipeStackAdapter(ArrayList<WordModel> data, Context context) {
        mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public WordModel getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.card, parent, false);
        }

        TextView textViewCard = (TextView) convertView.findViewById(R.id.textViewCard);
        textViewCard.setText(mData.get(position).getWord());

        TextView textViewCard2 = (TextView) convertView.findViewById(R.id.bn_pos);
        textViewCard2.setText(mData.get(position).getBanglaPOS());

        TextView textViewCard3 = (TextView) convertView.findViewById(R.id.en_pos);
        textViewCard3.setText(mData.get(position).getEnglishPOS());

        TextView textViewCard4 = (TextView) convertView.findViewById(R.id.bn_syn);
        textViewCard4.setText(mData.get(position).getBanglaDefination());

        TextView textViewCard5 = (TextView) convertView.findViewById(R.id.en_syn);
        textViewCard5.setText(mData.get(position).getEnglishDefination());
        convertView.findViewById(R.id.playBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String audioFileName = mData.get(position).getAudioFileName();
                Log.e("taggy", audioFileName);
                audioFileName = audioFileName.replace(".mp3", "");
//        audioFileName = audioFileName.replace(".", "");
                int resId = getResourceId(audioFileName, "raw", context.getPackageName());
                if( resId > 0) {
                    MediaPlayer mPlayer = MediaPlayer.create(context, resId);
                    mPlayer.start();
                }
                else{
                    Log.e("taggy", "PlayAudio : media file not found: " + audioFileName + " resId: " + resId);
                }
            }
        });

        return convertView;
    }

    public int getResourceId(String pVariableName, String pResourcename, String pPackageName) {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}