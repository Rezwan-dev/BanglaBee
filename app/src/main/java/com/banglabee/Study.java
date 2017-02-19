package com.banglabee;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.banglabee.mashroor.databasemanagement.Constants;
import com.banglabee.mashroor.databasemanagement.WordModel;
import com.cunoraz.gifview.library.GifView;
import com.banglabee.mashroor.databasemanagement.DataFetcer;
import com.banglabee.stack.SwipeStack;
import com.banglabee.stack.SwipeStackAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Study extends AppCompatActivity implements SwipeStack.SwipeStackListener {

    private SwipeStack mSwipeStack;
    private SwipeStackAdapter mAdapter;
    private ArrayList<String> mData;
    private GifView gifView1;

    public static final String isStudy  = "isStudy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        mSwipeStack = (SwipeStack) findViewById(R.id.swipeStack);
        SharedPreferences sharedPref = getSharedPreferences("spellingC", Context.MODE_PRIVATE);
        int dificulty = sharedPref.getInt("dificulty", 1);
        int quizSize = sharedPref.getInt("quizSize", 5);
       // fillWithTestData();
        if(getIntent().getBooleanExtra("isStudy",false)){
            fillWithStudyWrongData();
        }else {
            mAdapter = new SwipeStackAdapter(new DataFetcer(this).fetchData(quizSize, dificulty), this);
            mSwipeStack.setAdapter(mAdapter);
            gifView1 = (GifView)findViewById(R.id.gif);
            mSwipeStack.setListener(this);
        }

    }

    private void fillWithStudyWrongData() {
        ArrayList<Integer> integers = new bbDBHelper(this).getAllWrong();
        DataFetcer dataFetcer  = new DataFetcer(this);
        if(integers.size() < 1){
            return;
        }
        Set<Integer> set = new HashSet<Integer>();
        int max_size  = 10;
        if(integers.size() < max_size){
            max_size  = integers.size();
        }
        Random r = new Random();
        while (set.size() < max_size) {
            set.add(r.nextInt(integers.size()));
        }
        ArrayList<WordModel> wordModels  = new ArrayList<>();
        for (Integer integer : set){
            String number  = ""+integers.get(integer);
            int serial  = Integer.parseInt(number.substring(0,number.length()-1));
            int dificulty = Integer.parseInt(""+number.charAt(number.length()-1));
            wordModels.add(dataFetcer.fetchSingleData(serial,dificulty));
        }
        mAdapter = new SwipeStackAdapter(wordModels, this);
        mSwipeStack.setAdapter(mAdapter);
        gifView1 = (GifView)findViewById(R.id.gif);
        mSwipeStack.setListener(this);
    }

    private void fillWithTestData() {
        mData =  new ArrayList<>();
        for (int x = 0; x < 15; x++) {
            mData.add("Bae" + " " + (x + 1));
        }
    }

    @Override
    public void onViewSwipedToLeft(int position) {
        gifView1.play();
    }

    @Override
    public void onViewSwipedToRight(int position) {
        gifView1.play();
    }

    @Override
    public void onStackEmpty() {
        Toast.makeText(this,"Great Work", Toast.LENGTH_LONG).show();
    }
}
