package alex.aleksandr.ru.musicguitar;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import alex.aleksandr.ru.musicguitar.DAO.SongText;

public class SongTextActivity extends AppCompatActivity {

    public static final String EXTRA_ID_SONG = "alex.aleksandr.ru.musicguitar.extra_id_song";

    private MusicListDb db;
    private TextView textView;
    private int countAuthor;
    private long songId;
    private Toolbar toolbar;
    private SongText songText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_text);
        db = MusicListDb.getMusicDataBase(this);
        textView = (TextView) findViewById(R.id.textViewSongText);
        songId = getIntent().getLongExtra(EXTRA_ID_SONG, 0);


        toolbar = (Toolbar) findViewById(R.id.toolbar_song_text);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = db.querySongById(songId);
        cursor.moveToFirst();
        songText = SongText.fromCursor(cursor);
        cursor.close();
        countAuthor = db.querySongByAuthorName(songText.getAuthorName()).getCount();

        if (toolbar != null) {
            setTitle(songText.getName());
            toolbar.setSubtitle(songText.getAuthorName());
        }
        textView.setText(songText.getTextSong());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_song_text, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (item.getItemId() == R.id.menu_song_text_edit) {
            Intent i = new Intent(SongTextActivity.this, EditActivity.class);
            i.putExtra(EditActivity.EXTRA_IS_EDIT, true);
            i.putExtra(EditActivity.EXTRA_SONG_EDIT, songId);
            startActivity(i);
        } else if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menu_song_text_delete) {
            if (countAuthor == 1) {
                db.deleteSong(songId);
                db.deleteAuthor(songText.getAuthorName());
                finish();
            } else {
                db.deleteSong(songId);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
