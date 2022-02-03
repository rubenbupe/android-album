package com.example.practica1.Adapters;

import com.example.practica1.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArtistData {
    public String name;
    public ArrayList<AlbumData> albums;

    public static final ArrayList<ArtistData> Examples = new ArrayList<ArtistData>(Arrays.asList(
            new ArtistData("Artista 1", new ArrayList<>( Arrays.asList(
                    AlbumData.AlbumDataFactory.factoryMethod(),
                    AlbumData.AlbumDataFactory.factoryMethod()
            ))),
            new ArtistData("Artista 2", new ArrayList<>( Arrays.asList(
                    AlbumData.AlbumDataFactory.factoryMethod(),
                    AlbumData.AlbumDataFactory.factoryMethod()
            )))
    ));

    public ArtistData(String name){
        this.name = name;
        this.albums = new ArrayList<>();
    }

    public ArtistData(String name, ArrayList<AlbumData> albums){
        this.name = name;
        this.albums = albums;
    }
}
