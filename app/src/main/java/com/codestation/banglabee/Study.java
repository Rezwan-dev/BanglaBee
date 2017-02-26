package com.codestation.banglabee;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.codestation.banglabee.mashroor.databasemanagement.DataFetcer;
import com.codestation.banglabee.mashroor.databasemanagement.WordModel;
import com.codestation.banglabee.stack.SwipeStack;
import com.codestation.banglabee.stack.SwipeStackAdapter;
import com.cunoraz.gifview.library.GifView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Study extends AppCompatActivity implements SwipeStack.SwipeStackListener {

    private SwipeStack mSwipeStack;
    private SwipeStackAdapter mAdapter;
    private ArrayList<String> mData;
    private GifView gifView1;

    public static final String isStudy  = "isStudy";
    private LinearLayout searchLL;
    private EditText searchET;
    private ImageView searchBtn;
    private RecyclerView searchRV;

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

        searchLL  = (LinearLayout)findViewById(R.id.searchLL);
        searchET  = (EditText)findViewById(R.id.searchET);
        searchRV = (RecyclerView)findViewById(R.id.searchRV);
        searchBtn = (ImageView)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchET.getVisibility() == View.VISIBLE){
                    ViewGroup.LayoutParams parms = searchLL.getLayoutParams();
                    parms.width =  ViewGroup.LayoutParams.WRAP_CONTENT;
                    searchLL.setLayoutParams(parms);
                    searchET.setVisibility(View.GONE);
                    searchRV.setVisibility(View.GONE);

                }else{
                    ViewGroup.LayoutParams parms = searchLL.getLayoutParams();
                    parms.width =  ViewGroup.LayoutParams.MATCH_PARENT;
                    searchLL.setLayoutParams(parms);
                    searchET.setVisibility(View.VISIBLE);
                    searchRV.setVisibility(View.VISIBLE);
                    fillData();
                }
            }
        });


    }

    private void fillData(){
        searchRV.setLayoutManager(new LinearLayoutManager(this));
        searchRV.setAdapter(new SearchAdapter(this, new ArrayList<WordModel>()));
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
