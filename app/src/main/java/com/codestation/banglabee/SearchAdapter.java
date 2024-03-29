package com.codestation.banglabee;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.codestation.banglabee.mashroor.databasemanagement.WordModel;
import java.util.ArrayList;


/**
 * Created by rezwan on 2/7/17.
 */


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SimpleViewHolder> {
    private static final int DEFAULT_ITEM_COUNT = 15;

    private final AppCompatActivity mContext;
    private ArrayList<WordModel> items;




    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private final View root;
        private final TextView searchTV;

        public SimpleViewHolder(View view) {
            super(view);
            root = view;
            searchTV = (TextView) view.findViewById(R.id.searchTV);
        }
    }


    public SearchAdapter(AppCompatActivity context, ArrayList<WordModel> items) {
        mContext = context;
        this.items = items;
    }


    public void newData(ArrayList<WordModel> items){
        this.items.clear();
        this.items  = items;
        notifyDataSetChanged();
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.search_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        holder.searchTV.setText(items.get(position).getWord());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(items.get(position));
            }
        });
    }

    private void showDialog(WordModel wordModel) {
        SingleWord.getInstance(wordModel).show(mContext.getSupportFragmentManager(), "dialog");
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

}