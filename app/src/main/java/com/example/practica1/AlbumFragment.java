package com.example.practica1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practica1.Adapters.AlbumData;
import com.example.practica1.Adapters.AlbumRecycleViewAdapter;
import com.example.practica1.Adapters.ArtistData;
import com.example.practica1.Adapters.ArtistRecycleViewAdapter;

import java.util.ArrayList;

public class AlbumFragment extends Fragment implements AlbumRecycleViewAdapter.RecyclerViewClickListener {

    private int mArtistId;
    private AlbumRecycleViewAdapter mAdapter;
    private ArrayList<AlbumData> mData;


    public AlbumFragment(){

    }

    @Override
    public void onStart() {
        super.onStart();

        View v = getView();

        if(v != null){
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.albumList);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

            mData = ((ArtistData)ArtistData.Examples.get(mArtistId)).albums;

            mAdapter = new AlbumRecycleViewAdapter(getActivity(), this,
                    mData);
            recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        onItemClicked(position);
    }

    static interface Listener {
        void albumClicked(int artist, int position);
    }

    private AlbumFragment.Listener mListener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        mListener = (AlbumFragment.Listener) context;
    }

    public void onItemClicked(int position){
        if(mListener != null){
            mListener.albumClicked(mArtistId, position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    public void setArtistId(int id) {
        this.mArtistId = id;
    }

    public void addAlbum(String name) {
        mData.add(new AlbumData(name, R.drawable.ic_launcher_background));
        mAdapter.notifyItemInserted(mData.size() - 1);
    }

}