package com.codestation.banglabee;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.codestation.banglabee.mashroor.databasemanagement.WordModel;

import java.io.File;
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
        findViewById(R.id.studyWrong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(Stats.this, Study.class);
                intent.putExtra(Study.isStudy,true);
                startActivity(intent);
            }
        });
        String filePath = sharedPref.getString("saved", "");
        RoundedImage profile_pic = (RoundedImage) findViewById(R.id.pro_pic_stats);
        if (filePath.length() > 0) {
            File imgFile = new File(filePath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                profile_pic.setImageBitmap(myBitmap);

            }
        }
    }
}
