package com.banglabee;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.banglabee.mashroor.databasemanagement.WordModel;

import java.util.ArrayList;


public class Stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SharedPreferences sharedPref = getSharedPreferences("spellingC",Context.MODE_PRIVATE);
        ((TextView)findViewById(R.id.name_tv_stats)).setText(sharedPref.getString("name", "Player One"));
        ((TextView)findViewById(R.id.highestScore)).setText(""+sharedPref.getInt("highestScore", 0));
        ((TextView)findViewById(R.id.rightCount)).setText(""+sharedPref.getInt("rightCount", 0));
        ((TextView)findViewById(R.id.wrongCount)).setText(""+sharedPref.getInt("wrongCount", 0));
        findViewById(R.id.historyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bbDBHelper bbDBHelper  =  new bbDBHelper(Stats.this);
                ArrayList<HistoryDTO> historyDTOs =  bbDBHelper.getAllHistory();
                for (HistoryDTO history: historyDTOs){
                    Log.e("TAGGY", ""+history.key);
                    for(WordModel wordModel: history.wordModels){
                        Log.e("TAGGY", wordModel.toString());
                    }
                }
                ArrayList<Integer>integers = bbDBHelper.getAllWrong();
                for(Integer integer  : integers){
                    Log.e("TAGGY", ""+integer);
                }
            }
        });
    }
}
