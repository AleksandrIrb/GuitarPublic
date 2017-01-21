package alex.aleksandr.ru.musicguitar;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

public class SongActivity extends AppCompatActivity {

    public static final String EXTRA_IS_LAND_ORIENTATION_SONG = "alex.aleksandr.ru.musicguitar.is_land_orientation_song";

    private FragmentManager fragmentManager;
    private String name;
    private EditText editText;
    private boolean isLand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        name = getIntent().getStringExtra(AuthorActivity.EXTRA_NAME_AUTHOR);
        isLand = getIntent().getBooleanExtra(EXTRA_IS_LAND_ORIENTATION_SONG, true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(name);
        }

        fragmentManager = getSupportFragmentManager();
        editText = (EditText) findViewById(R.id.song_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateFragments(name);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFragments(name);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateFragments(String name) {
        String s = editText.getText().toString();

        SongListFragment songListFragment = new SongListFragment();
        fragmentManager.beginTransaction().replace(R.id.content_container, songListFragment).commit();

        Bundle bundle = new Bundle();
        bundle.putString(SongListFragment.EXTRA_NAME_AUTHOR_FRAGMENT, name);
        bundle.putString(SongListFragment.EXTRA_SEARCH_FILTER_SONG, s);
        bundle.putBoolean(SongListFragment.EXTRA_IS_LAND_ORIENTATION_SONG_FRAG, isLand);
        songListFragment.setArguments(bundle);
    }

}
