package com.example.practica1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.practica1.Adapters.AlbumData;
import com.example.practica1.Adapters.ArtistData;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ArtistsFragment.Listener,
        AlbumFragment.Listener {

    ArtistsFragment mListFragment;
    AlbumFragment mAlbumFragment;
    DetailFragment mDetailFragment;

    ArtistData mArtist;
    AlbumData mAlbum;

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListFragment = (ArtistsFragment)getSupportFragmentManager()
                .findFragmentById(R.id.artistsListFragment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        View albumContainer = findViewById(R.id.albumListContainer);
        if(albumContainer == null) {
            getMenuInflater().inflate(R.menu.menu_toolbar_add, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_toolbar_tablet, menu);
            hideShare();
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void hideShare() {

        MenuItem item = mMenu.findItem(R.id.menuShare);
        item.setVisible(false);
    }


    private void showShare() {

        MenuItem item = mMenu.findItem(R.id.menuShare);
        item.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuAdd:
                if (findViewById(R.id.albumListContainer) != null)
                    showTabletAddDialog();
                else {
                    showAddDialog();
                }
                break;
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

    private void showAddDialog() {
        //Como solo necesitamos un edittext y no es necesario darle estilo, lo creamos dinámicamente
        EditText inputEditText = new EditText(this);
        inputEditText.setHint("Nombre");
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.add_artist_title)
                .setMessage(R.string.add_artist_message)
                .setView(inputEditText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String editTextInput = inputEditText.getText().toString().trim();
                        if(!editTextInput.isEmpty())
                            mListFragment.addArtist(editTextInput);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void showTabletAddDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        ArrayList<String> spinnerArray = new ArrayList<String>(Arrays.asList("Artista"));
        if(mAlbumFragment != null)
            spinnerArray.add("Álbum"); // Strings mágicos (fuera del alcance de esta práctica)
        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);
        layout.addView(spinner);

        final EditText editText = new EditText(this);
        editText.setHint("Nombre");
        layout.addView(editText);

        EditText inputEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.add_element)
                .setView(layout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handleTabletDialogInput(spinner, editText);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    /**
     *
     * @throws Exception si se intenta crear un álbum pero el fragmento no existe todavía
     * (mAlbumFragment)
     */
    public void handleTabletDialogInput(Spinner s, EditText e){
        String name = e.getText().toString().trim();
        switch (s.getSelectedItem().toString()){
            case "Álbum":
                if(!name.isEmpty())
                    mAlbumFragment.addAlbum(name);
                break;
            case "Artista":
                if(!name.isEmpty())
                    mListFragment.addArtist(name);
                break;
        }
    }




    @Override
    public void artistClicked(int position) {
        View albumContainer = findViewById(R.id.albumListContainer);
        if(albumContainer != null){
            AlbumFragment albumFragment = new AlbumFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            albumFragment.setArtistId(position);
            transaction.add(R.id.albumListContainer, albumFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(null);
            if(mDetailFragment != null) {
                transaction.remove(mDetailFragment);
                hideShare();
            }
            if(mAlbumFragment != null)
                transaction.remove(mAlbumFragment);
            mAlbumFragment = albumFragment;
            transaction.commit();

        }else{
            Intent intent = new Intent(this, AlbumActivity.class);
            intent.putExtra("ARTIST_ID", (int) position);
            startActivity(intent);
        }
    }

    @Override
    public void albumClicked(int artist, int position) {
        View albumContainer = findViewById(R.id.albumListContainer);
        this.mArtist = ((ArtistData)ArtistData.Examples.get(artist));
        this.mAlbum = ((ArtistData)ArtistData.Examples.get(artist)).albums.get(position);

        if(albumContainer != null){
            DetailFragment detailFragment = new DetailFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            detailFragment.setAlbumId(mArtist, mAlbum);
            transaction.add(R.id.detailContainer, detailFragment);
            if(mDetailFragment != null) {
                transaction.remove(mDetailFragment);
            }
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(null);
            transaction.commit();
            mDetailFragment = detailFragment;
            showShare();

        }
    }
}