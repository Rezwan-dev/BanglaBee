package com.codestation.banglabee;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.codestation.banglabee.mashroor.databasemanagement.DataFetcer;
import com.cunoraz.gifview.library.GifView;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

public class Play extends AppCompatActivity {

    private GifView gifView1;
    private TextView question_1, question_2;
    private LayoutAdapter adapter;
    private MediaPlayer mPlayer;
    private ImageView playSbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        gifView1 = (GifView)findViewById(R.id.gif);
        question_1  = (TextView)findViewById(R.id.question_1);
        question_2  = (TextView)findViewById(R.id.question_2);
        final RecyclerViewPager mRecyclerView = (RecyclerViewPager) findViewById(R.id.rvp);
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerView.setTriggerOffset(0.15f);
        mRecyclerView.setFlingFactor(0.25f);
        mRecyclerView.setLayoutManager(layout);
        SharedPreferences sharedPref = getSharedPreferences("spellingC", Context.MODE_PRIVATE);
        int dificulty = sharedPref.getInt("dificulty", 1);
        int quizSize = sharedPref.getInt("quizSize", 5);
        question_2.setText("Score: 0/"+(quizSize*dificulty));
        adapter  = new LayoutAdapter(this,mRecyclerView,new DataFetcer(this).fetchData(quizSize,dificulty));
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

        playSbtn = (ImageView)findViewById(R.id.playSbtn);
        playSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayer == null || !mPlayer.isPlaying()) {
                    playAudio(adapter.getCurrentAudioPath());
                }else {
                    stop();
                }
            }
        });

       /* findViewById(R.id.playSbtn).setOnClickListener(new View.OnClickListener() {
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
        });*/

    }

    private void playAudio(String audioFileName) {
        Log.e("taggy", audioFileName);
        audioFileName = audioFileName.replace(".mp3", "");
//        audioFileName = audioFileName.replace(".", "");
        int resId = getResourceId(audioFileName, "raw", getPackageName());
        if( resId > 0) {
            play(resId);
        }
        else{
            Log.e("taggy", "PlayAudio : media file not found: " + audioFileName + " resId: " + resId);
        }
    }

    public int getResourceId(String pVariableName, String pResourcename, String pPackageName) {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void play(int id){
        stop();
        mPlayer = MediaPlayer.create(this, id);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });
        if(!gifView1.isPlaying()) {
            gifView1.play();
        }
        playSbtn.setImageDrawable(getResources().getDrawable(R.drawable.media_stop));
        mPlayer.start();
    }
    public void stop(){
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        if(gifView1.isPlaying()) {
            gifView1.pause();
        }
        playSbtn.setImageDrawable(getResources().getDrawable(R.drawable.media_play));
    }
}
