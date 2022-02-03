package com.example.practica1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.practica1.Adapters.AlbumData;
import com.example.practica1.Adapters.ArtistData;

public class DetailActivity extends AppCompatActivity {

    private DetailFragment mDetailFragment;
    int mArtistId, mAlbumId;
    public ArtistData mArtist;
    public AlbumData mAlbum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mArtistId = (int) getIntent().getExtras().get("ARTIST_ID");
        mAlbumId = (int) getIntent().getExtras().get("ALBUM_ID");
        this.mArtist = ((ArtistData)ArtistData.Examples.get(mArtistId));
        this.mAlbum = ((ArtistData)ArtistData.Examples.get(mArtistId)).albums.get(mAlbumId);

        mDetailFragment = (DetailFragment)getSupportFragmentManager()
                .findFragmentById(R.id.detailFragment);

        mDetailFragment.setAlbumId(mArtist, mAlbum);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuShare:
                showShareDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showShareDialog(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Detalles del album '" + mAlbum.name + "'");
        intent.putExtra(Intent.EXTRA_TEXT, mAlbum.name + "\n" + mArtist.name);

        startActivity(Intent.createChooser(intent, "Enviar detalles por Email"));
    }

}