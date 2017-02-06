package com.example.rezwan.spellingc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cunoraz.gifview.library.GifView;
import com.example.rezwan.spellingc.stack.SwipeStack;
import com.example.rezwan.spellingc.stack.SwipeStackAdapter;

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
        fillWithTestData();
        mAdapter = new SwipeStackAdapter(mData, this);
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

    }
}
