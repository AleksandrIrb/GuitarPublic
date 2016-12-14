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
    private static int getCountAuthor;
    private static long getSongId;
    private Cursor cursor;

    public static final String EXTRA_ID_SONG_ID = "alex.aleksandr.ru.musicguitar.extra_id_song_text";
    public static final String EXTRA_ID_SONG_COUNT = "alex.aleksandr.ru.musicguitar.extra_id_song_count";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_text);

        db = new MusicListDb(this);
        getSongId = getIntent().getLongExtra(EXTRA_ID_SONG_ID, 0);
        getCountAuthor = getIntent().getIntExtra(EXTRA_ID_SONG_COUNT, 0);
        cursor = db.querySelectSong("_id= ?", new String[]{String.valueOf(getSongId)});
        cursor.moveToFirst();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_song_text);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(cursor.getString(cursor.getColumnIndex(MusicListDb.getSongName())));
        }

        textView = (TextView) findViewById(R.id.textViewSongText);


    }

    @Override
    protected void onResume() {
        super.onResume();
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
            i.putExtra(EditActivity.EXTRA_IS_EDIT, true);
            i.putExtra(EditActivity.EXTRA_ID_SONG_EDIT_ID, getSongId);
            startActivity(i);
        } else if (id == android.R.id.home) {
            finish();
        } else if(id == R.id.menu_song_text_delete)
        {
            deleteList();
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteList() {
        if(getCountAuthor == 1) {
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
