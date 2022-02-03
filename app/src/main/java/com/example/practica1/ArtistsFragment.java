package com.example.practica1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practica1.Adapters.ArtistData;
import com.example.practica1.Adapters.ArtistRecycleViewAdapter;

import java.util.ArrayList;

public class ArtistsFragment extends Fragment implements ArtistRecycleViewAdapter.RecyclerViewClickListener {

    private ArtistRecycleViewAdapter mAdapter;
    private ArrayList<ArtistData> mData;

    public ArtistsFragment(){

    }

    @Override
    public void onStart() {
        super.onStart();

        View v = getView();

        if(v != null){
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.artistList);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

            mData = ArtistData.Examples;

            mAdapter = new ArtistRecycleViewAdapter(getActivity(),
                    this,  mData);
            recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        onItemClicked(position);
    }

    static interface Listener {
        void artistClicked(int position);
    }

    private Listener mListener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        mListener = (Listener) context;
    }

    public void onItemClicked(int position){
        if(mListener != null){
            mListener.artistClicked(position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

    public void addArtist(String name) {
        mData.add(new ArtistData(name));
        mAdapter.notifyItemInserted(mData.size() - 1);
    }

}