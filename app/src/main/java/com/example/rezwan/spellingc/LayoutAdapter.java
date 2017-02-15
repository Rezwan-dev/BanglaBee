package com.example.rezwan.spellingc;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by rezwan on 2/7/17.
 */


public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {
    private static final int DEFAULT_ITEM_COUNT = 15;

    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private final List<Integer> mItems;
    private int mCurrentItemId = 0;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        private MorphingButton morph;
        // public final TextView title;

        public SimpleViewHolder(View view) {
            super(view);
            morph = (MorphingButton)view.findViewById(R.id.morph);
          //  title = (TextView) view.findViewById(R.id.title);
        }
    }

    public LayoutAdapter(Context context, RecyclerView recyclerView) {
        this(context, recyclerView, DEFAULT_ITEM_COUNT);
    }

    public LayoutAdapter(Context context, RecyclerView recyclerView, int itemCount) {
        mContext = context;
        mItems = new ArrayList<>(itemCount);
        for (int i = 0; i < itemCount; i++) {
            addItem(i);
        }

        mRecyclerView = recyclerView;
    }

    public void addItem(int position) {
        final int id = mCurrentItemId++;
        mItems.add(position, id);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.card_2, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        holder.morph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setOnClickListener(null);
                Resources r = v.getContext().getResources();
                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, r.getDisplayMetrics());
                if(position/2 == 0) {
                    MorphingButton.Params circle = MorphingButton.Params.create()
                            .duration(500)
                            .cornerRadius(px) // 56 dp
                            .width(px) // 56 dp
                            .height(px) // 56 dp
                            .color(r.getColor(R.color.green)) // normal state color
                            .colorPressed(r.getColor(R.color.green)) // pressed state color
                            .icon(R.drawable.correct); // icon
                    holder.morph.morph(circle);
                }else{
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
            }
        });
        final int itemId = mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}