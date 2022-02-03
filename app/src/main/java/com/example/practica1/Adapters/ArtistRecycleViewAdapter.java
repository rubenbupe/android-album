package com.example.practica1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica1.R;

import java.util.ArrayList;
import java.util.List;

public class ArtistRecycleViewAdapter extends RecyclerView.Adapter<ArtistRecycleViewAdapter.MyViewHolder>{

    private ArrayList<ArtistData> mData;
    private Context mContext;
    private static RecyclerViewClickListener itemListener;

    public interface RecyclerViewClickListener {
        public void recyclerViewListClicked(View v, int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout)itemView;
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }

    public ArtistRecycleViewAdapter(Context context, RecyclerViewClickListener itemListener, ArrayList<ArtistData> data) {
        mContext=context;
        mData = data;
        ArtistRecycleViewAdapter.itemListener = itemListener;
    }

    @Override
    public ArtistRecycleViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.artist_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int pos) {
        ((TextView)holder.linearLayout.findViewById(R.id.artistName)).setText(mData.get(pos).name);
    }

    @Override
    public int getItemCount() {return mData.size();}

}



