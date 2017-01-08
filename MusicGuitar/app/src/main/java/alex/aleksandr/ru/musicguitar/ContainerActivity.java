package alex.aleksandr.ru.musicguitar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class ContainerActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private SongListFragment songListFragment = null;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        name = getIntent().getStringExtra(AuthorActivity.EXTRA_NAME_AUTHOR);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(name);
        }

        fragmentManager = getSupportFragmentManager();
        updateFragments(name);

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
        if(songListFragment != null) {
            fragmentManager.beginTransaction().
                    remove(songListFragment).commit();
        }
        songListFragment = new SongListFragment();
        fragmentManager.beginTransaction().add(R.id.content_container, songListFragment).commit();

        Bundle bundle = new Bundle();
        bundle.putString(SongListFragment.EXTRA_NAME_AUTHOR_FRAGMENT, name);
        songListFragment.setArguments(bundle);
    }

}
