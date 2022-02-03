package com.example.practica1.Adapters;

import com.example.practica1.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AlbumData {
    public String name;
    public int imgId;

    public AlbumData(String name, int imgId){
        this.name = name;
        this.imgId = imgId;
    }

    public static class AlbumDataFactory {
        private static int counter = 1;
        public static AlbumData factoryMethod(){
            return new AlbumData("Album " + String.valueOf(counter++),
                    (Math.random() <= 0.5) ?
                            R.drawable.ic_launcher_background : R.drawable.ic_launcher_foreground);
        }
    }

}
