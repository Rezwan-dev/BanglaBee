package com.codestation.banglabee;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codestation.banglabee.mashroor.databasemanagement.WordModel;

/**
 * Created by rezwan on 2/11/17.
 */

public class SingleWord extends DialogFragment {



    private View convertView;
    private static WordModel wordModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.MyDialog);
    }
    public static SingleWord getInstance(WordModel wordModel){
        SingleWord.wordModel = wordModel;
        return new SingleWord();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.card, container, false);
        convertView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        return convertView;
    }
    
    public void init(){
        TextView textViewCard = (TextView) convertView.findViewById(R.id.textViewCard);
        textViewCard.setText(wordModel.getWord());

        TextView textViewCard2 = (TextView) convertView.findViewById(R.id.bn_pos);
        textViewCard2.setText(wordModel.getBanglaPOS());

        TextView textViewCard3 = (TextView) convertView.findViewById(R.id.en_pos);
        textViewCard3.setText(wordModel.getEnglishPOS());

        TextView textViewCard4 = (TextView) convertView.findViewById(R.id.bn_syn);
        textViewCard4.setText(wordModel.getBanglaDefination());

        TextView textViewCard5 = (TextView) convertView.findViewById(R.id.en_syn);
        textViewCard5.setText(wordModel.getEnglishDefination());
        convertView.findViewById(R.id.playBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String audioFileName = wordModel.getAudioFileName();
                Log.e("taggy", audioFileName);
                audioFileName = audioFileName.replace(".mp3", "");
//        audioFileName = audioFileName.replace(".", "");
                int resId = getResourceId(audioFileName, "raw", getActivity().getPackageName());
                if( resId > 0) {
                    MediaPlayer mPlayer = MediaPlayer.create(getContext(), resId);
                    mPlayer.start();
                }
                else{
                    Log.e("taggy", "PlayAudio : media file not found: " + audioFileName + " resId: " + resId);
                }
            }
        });
    }

    private int getResourceId(String pVariableName, String pResourcename, String pPackageName) {
        try {
            return getActivity().getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
