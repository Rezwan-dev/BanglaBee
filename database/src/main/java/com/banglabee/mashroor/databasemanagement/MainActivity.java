package com.banglabee.mashroor.databasemanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataFetcer df = new DataFetcer(getApplicationContext());

        ArrayList<WordModel> temp = df.fetchData(10, 3);

        String tempStr = new String();

        for(WordModel words : temp)
        {
            Log.e("SB", words.toString());
        }



//        Log.e("SB", tempStr);

    }


    public void test(View view) {

        DataFetcer df = new DataFetcer(getApplicationContext());

        ArrayList<WordModel> temp = df.fetchData(10, 3);

        String tempStr = new String();

        for(WordModel words : temp)
        {
            tempStr += words.toString();
            //Log.e("SB", words.toString());
        }
        ((TextView)view).setText(tempStr);
    }
}
