package alex.aleksandr.ru.musicguitar;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SongTextActivity extends AppCompatActivity {

    private MusicListDb db;
    private TextView textView;

    public static final String EXTRA_ID_SONG_TEXT = "alex.aleksandr.ru.musicguitar.extra_id_song_text";
    public static final String EXTRA_ID_SONG_COUNT = "alex.aleksandr.ru.musicguitar.extra_id_song_count";
    private static int getCountAuthor;
    private static long getSongId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_text);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_song_text);
        setSupportActionBar(toolbar);

        db = new MusicListDb(this);
        getSongId = getIntent().getLongExtra(EXTRA_ID_SONG_TEXT, 0);
        getCountAuthor = getIntent().getIntExtra(EXTRA_ID_SONG_COUNT, 0);
        Cursor cursor = db.querySelectSong("_id= ?", new String[]{String.valueOf(getSongId)});
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
        } else if(id == R.id.menu_song_text_delete)
        {
            DialogWindow dialogWindow = new DialogWindow();
            dialogWindow.show(getSupportFragmentManager(), "deleteList");
            deleteList();
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteList() {
        if(getCountAuthor == 1) {

            Cursor cursor = db.querySelectSong("_id= ?", new String[]{String.valueOf(getSongId)});
            cursor.moveToFirst();
            String author = cursor.getString(cursor.getColumnIndex(MusicListDb.getSongAuthor()));
            db.deleteSong(getSongId);
            db.deleteAuthor(author);
            finish();

        } else {
            db.deleteSong(getSongId);
            finish();
        }

    }

}
