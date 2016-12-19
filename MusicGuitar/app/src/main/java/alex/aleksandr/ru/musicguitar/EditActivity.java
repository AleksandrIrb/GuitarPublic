package alex.aleksandr.ru.musicguitar;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditActivity extends AppCompatActivity {

    private EditText author;
    private EditText songname;
    private EditText songtext;
    private Button addInDatabase;
    private boolean isEdit;
    private long id;
    private MusicListDb db;
    private String oldAuthor;

    public static final String EXTRA_IS_EDIT = "alex.aleksandr.ru.musicguitar.extra_is_edit";
    public static final String EXTRA_SONG_EDIT = "alex.aleksandr.ru.musicguitar.extra_song_edit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        author = (EditText) findViewById(R.id.editNameAuthor);
        songname = (EditText) findViewById(R.id.editNameSong);
        songtext = (EditText) findViewById(R.id.editTextSong);
        addInDatabase = (Button) findViewById(R.id.buttonAdd);
        db = MusicListDb.getMusicDataBase(this);

        isEdit = getIntent().getBooleanExtra(EXTRA_IS_EDIT, false);
        id = getIntent().getLongExtra(EXTRA_SONG_EDIT, 0);

        if (isEdit) {
            Cursor cursor = db.querySelectSong("_id= ?", new String[]{String.valueOf(id)});
            cursor.moveToFirst();
            oldAuthor = SongText.fromCursor(cursor).getAuthorName();
            author.setText(oldAuthor);
            songname.setText(SongText.fromCursor(cursor).getName());
            songtext.setText(SongText.fromCursor(cursor).getTextSong());
        }

        addInDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = author.getText().toString();
                String sn = songname.getText().toString();
                String stxt = songtext.getText().toString();

                if (!a.isEmpty() && !sn.isEmpty() && !stxt.isEmpty()) {


                    if (isEdit) {
                        boolean isYesUpdate = db.updateListSong(a, sn, stxt, id, oldAuthor);
                        if (isYesUpdate) {
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Ошибка обновления данных", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        db.addListAuthor(a);
                        boolean isYesAdd = db.addListSong(a, sn, stxt);
                        if (isYesAdd) {
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Ошибка добавления данных", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
