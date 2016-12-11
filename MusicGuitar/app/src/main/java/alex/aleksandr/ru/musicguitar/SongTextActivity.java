package alex.aleksandr.ru.musicguitar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SongTextActivity extends AppCompatActivity {

    private MusicListDb db;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_song_text);
        setSupportActionBar(toolbar);

        db = new MusicListDb(this);
        db.getWritableDatabase();
        Cursor cursor = db.querySelectSong(MusicListDb.getSongAuthor() + "= ? AND "
                + MusicListDb.getSongName() + "= ?", new String[] {"new", "new"});
        cursor.moveToFirst();
        textView = (TextView) findViewById(R.id.textViewSongText);
        textView.setText(cursor.getString(cursor.getColumnIndex(MusicListDb.getSongText())));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_song_text, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_song_text_edit) {
            Intent i = new Intent(SongTextActivity.this, EditActivity.class);
            startActivity(i);
        } else if (id == R.id.menu_song_text_close) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
