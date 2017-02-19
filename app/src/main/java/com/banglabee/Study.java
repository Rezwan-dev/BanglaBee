package com.banglabee;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cunoraz.gifview.library.GifView;
import com.banglabee.mashroor.databasemanagement.DataFetcer;
import com.banglabee.stack.SwipeStack;
import com.banglabee.stack.SwipeStackAdapter;

import java.util.ArrayList;

public class Study extends AppCompatActivity implements SwipeStack.SwipeStackListener {

    private SwipeStack mSwipeStack;
    private SwipeStackAdapter mAdapter;
    private ArrayList<String> mData;
    private GifView gifView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        mSwipeStack = (SwipeStack) findViewById(R.id.swipeStack);
        SharedPreferences sharedPref = getSharedPreferences("spellingC", Context.MODE_PRIVATE);
        int dificulty = sharedPref.getInt("dificulty", 1);
        int quizSize = sharedPref.getInt("quizSize", 5);
       // fillWithTestData();
        mAdapter = new SwipeStackAdapter(new DataFetcer(this).fetchData(quizSize,dificulty), this);
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
