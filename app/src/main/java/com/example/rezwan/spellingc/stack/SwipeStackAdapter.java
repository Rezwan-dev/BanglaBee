package com.example.rezwan.spellingc.stack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mashroor.databasemanagement.WordModel;
import com.example.rezwan.spellingc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rezwan on 2/5/17.
 */

public class SwipeStackAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private ArrayList<WordModel> mData;

    public SwipeStackAdapter(ArrayList<WordModel> data, Context context) {
        mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public WordModel getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.card, parent, false);
        }

        TextView textViewCard = (TextView) convertView.findViewById(R.id.textViewCard);
        textViewCard.setText(mData.get(position).getWord());

        TextView textViewCard2 = (TextView) convertView.findViewById(R.id.bn_pos);
        textViewCard2.setText(mData.get(position).getBanglaPOS());

        TextView textViewCard3 = (TextView) convertView.findViewById(R.id.en_pos);
        textViewCard3.setText(mData.get(position).getEnglishPOS());

        TextView textViewCard4 = (TextView) convertView.findViewById(R.id.bn_syn);
        textViewCard4.setText(mData.get(position).getBanglaDefination());

        TextView textViewCard5 = (TextView) convertView.findViewById(R.id.en_syn);
        textViewCard5.setText(mData.get(position).getEnglishDefination());


        return convertView;
    }
}