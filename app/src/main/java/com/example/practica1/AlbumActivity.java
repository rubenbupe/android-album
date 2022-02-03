package com.example.practica1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class AlbumActivity extends AppCompatActivity implements AlbumFragment.Listener {

    int mArtistId;
    AlbumFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        mListFragment = (AlbumFragment)getSupportFragmentManager()
                .findFragmentById(R.id.albumListFragment);

        mArtistId = (int) getIntent().getExtras().get("ARTIST_ID");
        mListFragment.setArtistId(mArtistId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuAdd:
                showAddDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
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
                            mListFragment.addAlbum(editTextInput);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    public void albumClicked(int artist, int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("ALBUM_ID", (int) position);
        intent.putExtra("ARTIST_ID", (int) artist);
        startActivity(intent);
    }
}