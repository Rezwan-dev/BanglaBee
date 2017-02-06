package com.example.rezwan.spellingc.stack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rezwan.spellingc.R;

import java.util.List;

/**
 * Created by rezwan on 2/5/17.
 */

public class SwipeStackAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private List<String> mData;

    public SwipeStackAdapter(List<String> data, Context context) {
        mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
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
        textViewCard.setText(mData.get(position));

        return convertView;
    }
}