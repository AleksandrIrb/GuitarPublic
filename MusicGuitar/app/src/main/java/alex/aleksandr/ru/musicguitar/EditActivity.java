package alex.aleksandr.ru.musicguitar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        author = (EditText) findViewById(R.id.editNameAuthor);
        songname = (EditText) findViewById(R.id.editNameSong);
        songtext = (EditText) findViewById(R.id.editTextSong);
        addInDatabase = (Button) findViewById(R.id.buttonAdd);
        addInDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = author.getText().toString();
                String sn = songname.getText().toString();
                String stxt = songtext.getText().toString();

                if (!a.isEmpty() && !sn.isEmpty() && !stxt.isEmpty()) {

                    MusicListDb db = new MusicListDb(EditActivity.this);
                    boolean isAddInList = db.addSongInDatabase(a, sn, stxt);
                    if (isAddInList) {
                        Toast.makeText(getApplicationContext(), "Запись добавлена", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Ошибка добавления", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_edit_close) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
