package com.example.rezwan.spellingc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.example.mashroor.databasemanagement.DataFetcer;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

public class Play extends AppCompatActivity {

    private GifView gifView1;
    private TextView question_1, question_2;
    private Handler handler;
    private LayoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        gifView1 = (GifView)findViewById(R.id.gif);
        question_1  = (TextView)findViewById(R.id.question_1);
        question_2  = (TextView)findViewById(R.id.question_2);
        RecyclerViewPager mRecyclerView = (RecyclerViewPager) findViewById(R.id.rvp);
        handler =  new Handler();
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerView.setTriggerOffset(0.15f);
        mRecyclerView.setFlingFactor(0.25f);
        mRecyclerView.setLayoutManager(layout);
        SharedPreferences sharedPref = getSharedPreferences("spellingC", Context.MODE_PRIVATE);
        int dificulty = sharedPref.getInt("dificulty", 1);
        int quizSize = sharedPref.getInt("quizSize", 5);
        question_2.setText("Score: 0/"+(quizSize*dificulty));
        adapter  = new LayoutAdapter(this, mRecyclerView,new DataFetcer(this).fetchData(quizSize,dificulty), (ImageView)findViewById(R.id.playSbtn));
//        adapter  = new LayoutAdapter(this, mRecyclerView,new DataFetcer(this).fetchData(99,dificulty), (ImageView)findViewById(R.id.playSbtn));
        adapter.setOnScoreUpdate(new LayoutAdapter.OnScoreUpdate() {
            @Override
            public void updateScore(String score) {
                question_2.setText(score);
            }

            @Override
            public void onGameEnd(String score) {
                Congrats.getInstance(score).show(getSupportFragmentManager(), "dialog");
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
                question_1.setText("Question: "+(1+newPosition)+"/"+adapter.getItemCount());
            }
        });
        question_1.setText("Question: "+1+"/"+quizSize);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mPositionText.setText("First: " + mRecyclerViewPager.getFirstVisiblePosition());
                int childCount = recyclerView.getChildCount();
                int width = recyclerView.getChildAt(0).getWidth();
                int padding = (recyclerView.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                    }
                }
            }
        });

        findViewById(R.id.playSbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                if(!gifView1.isPlaying()) {
                    gifView1.play();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gifView1.pause();
                        }
                    },2000);
                }else{
                    gifView1.pause();
                }
            }
        });

    }
}
