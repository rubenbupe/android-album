package com.example.practica1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practica1.Adapters.AlbumData;
import com.example.practica1.Adapters.AlbumRecycleViewAdapter;
import com.example.practica1.Adapters.ArtistData;

public class DetailFragment extends Fragment {

    public ArtistData mArtist;
    public AlbumData mAlbum;

    @Override
    public void onStart() {
        super.onStart();

        View v = getView();

        if(v != null){
            ((TextView) v.findViewById(R.id.albumText)).setText(this.mAlbum.name);
            ((TextView) v.findViewById(R.id.artistText)).setText(this.mArtist.name);
            ((ImageView) v.findViewById(R.id.albumImage)).setImageResource(this.mAlbum.imgId);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    public void setAlbumId(ArtistData artist, AlbumData album){

        this.mArtist = artist;
        this.mAlbum = album;

    }
}