package com.example.rezwan.spellingc;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;
import com.example.mashroor.databasemanagement.WordModel;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.background;
import static android.R.attr.cacheColorHint;
import static android.R.attr.id;

/**
 * Created by rezwan on 2/7/17.
 */


public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {
    private static final int DEFAULT_ITEM_COUNT = 15;

    private final Context mContext;
    private final RecyclerViewPager mRecyclerView;
    private ArrayList<WordModel> items;
    private ImageView playBtn;
    private int mCurrentItemId = 0;
    private int answerCounter;
    private int score;
    private OnScoreUpdate onScoreUpdate;


    public void setOnScoreUpdate(LayoutAdapter.OnScoreUpdate onScoreUpdate) {
        this.onScoreUpdate = onScoreUpdate;
    }


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private MorphingButton morph;
        private TextView textViewCard2, textViewCard3, textViewCard4, textViewCard5;
        EditText editTv;
        // public final TextView title;

        public SimpleViewHolder(View view) {
            super(view);
            morph = (MorphingButton) view.findViewById(R.id.morph);
            textViewCard2 = (TextView) view.findViewById(R.id.bn_pos);
            textViewCard3 = (TextView) view.findViewById(R.id.en_pos);
            textViewCard4 = (TextView) view.findViewById(R.id.bn_syn);
            textViewCard5 = (TextView) view.findViewById(R.id.en_syn);
            editTv = (EditText) view.findViewById(R.id.editTv);
            //  title = (TextView) view.findViewById(R.id.title);
        }
    }


    public LayoutAdapter(Context context, RecyclerViewPager recyclerView, ArrayList<WordModel> items, ImageView playBtn) {
        mContext = context;
        mRecyclerView = recyclerView;
        this.items = items;
        this.playBtn = playBtn;
    }

    public int getResourceId(String pVariableName, String pResourcename, String pPackageName) {
        try {
            return mContext.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.card_2, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        final Resources r = mContext.getResources();
        final int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, r.getDisplayMetrics());
        switch (items.get(position).getStatus()) {

            case -1: {
                holder.morph.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.morph.setOnClickListener(null);
                        String word = holder.editTv.getText().toString().trim();
                        answerCounter++;
                        Log.e("tggy", word + " " + items.get(position).getWord());

                        if (word.equals(items.get(position).getWord())) {
                            items.get(position).setStatus(-1);
                            MorphingButton.Params circle = MorphingButton.Params.create()
                                    .duration(500)
                                    .cornerRadius(px) // 56 dp
                                    .width(px) // 56 dp
                                    .height(px) // 56 dp
                                    .color(r.getColor(R.color.green)) // normal state color
                                    .colorPressed(r.getColor(R.color.green)) // pressed state color
                                    .icon(R.drawable.correct); // icon
                            holder.morph.morph(circle);
                            score++;
                        } else {
                            MorphingButton.Params circle = MorphingButton.Params.create()
                                    .duration(500)
                                    .cornerRadius(px) // 56 dp
                                    .width(px) // 56 dp
                                    .height(px) // 56 dp
                                    .color(r.getColor(R.color.red)) // normal state color
                                    .colorPressed(r.getColor(R.color.red)) // pressed state color
                                    .icon(R.drawable.wrong); // icon
                            holder.morph.morph(circle);
                        }
                        fireListener();
                    }
                });
                break;
            }
            case 2: {
                holder.editTv.setEnabled(false);
                holder.morph.setOnClickListener(null);
                MorphingButton.Params circle = MorphingButton.Params.create()
                        .duration(0)
                        .cornerRadius(px) // 56 dp
                        .width(px) // 56 dp
                        .height(px) // 56 dp
                        .color(r.getColor(R.color.red)) // normal state color
                        .colorPressed(r.getColor(R.color.red)) // pressed state color
                        .icon(R.drawable.wrong); // icon
                holder.morph.morph(circle);
                break;
            }
            case 1: {
                holder.editTv.setEnabled(false);
                holder.morph.setOnClickListener(null);
                MorphingButton.Params circle = MorphingButton.Params.create()
                        .duration(0)
                        .cornerRadius(px) // 56 dp
                        .width(px) // 56 dp
                        .height(px) // 56 dp
                        .color(r.getColor(R.color.green)) // normal state color
                        .colorPressed(r.getColor(R.color.green)) // pressed state color
                        .icon(R.drawable.correct); // icon
                holder.morph.morph(circle);
                break;
            }

        }
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Taggy", "" + position);
                try{
                    playAudio(items.get(mRecyclerView.getCurrentPosition()).getAudioFileName());
                }
                catch (Exception e)
                {
                    Log.e("taggy", "Play audio exception: " + e);
                }

            }
        });
        holder.textViewCard2.setText(items.get(position).getBanglaPOS());

        holder.textViewCard3.setText(items.get(position).getEnglishPOS());

        holder.textViewCard4.setText(items.get(position).getBanglaDefination());

        holder.textViewCard5.setText(items.get(position).getEnglishDefination());

    }

    private void fireListener() {
        if(onScoreUpdate != null){
            if(answerCounter == items.size()){
                onScoreUpdate.onGameEnd("Score: "+(score*items.get(0).getWeight())+"/"+(items.size()*items.get(0).getWeight()));
            }else{
                onScoreUpdate.updateScore("Score: "+(score*items.get(0).getWeight())+"/"+(items.size()*items.get(0).getWeight()));
            }
        }
    }

    private void playAudio(String audioFileName) {
        Log.e("taggy", audioFileName);
        audioFileName = audioFileName.replace(".mp3", "");
//        audioFileName = audioFileName.replace(".", "");
        int resId = getResourceId(audioFileName, "raw", mContext.getPackageName());
        if( resId > 0) {
            MediaPlayer mPlayer = MediaPlayer.create(mContext, resId);
            mPlayer.start();
        }
        else{
            Log.e("taggy", "PlayAudio : media file not found: " + audioFileName + " resId: " + resId);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnScoreUpdate {
        void updateScore(String score);
        void onGameEnd(String score);
    }
}