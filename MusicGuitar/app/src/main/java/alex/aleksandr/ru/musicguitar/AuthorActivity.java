package alex.aleksandr.ru.musicguitar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;


public class AuthorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_NAME_AUTHOR = "alex.aleksandr.ru.musicguitar.extra_name_author";

    private DrawerLayout drawerLayout;
    private FrameLayout frameForSong = null;
    private boolean is_land;
    private AuthorListFragment authorListFragment = null;
    private FragmentManager fragmentManager;
    private EditText editTextSearchAuthor;
    private EditText editTextSearchSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        frameForSong = (FrameLayout) findViewById(R.id.frame_layout_for_song_in_author);
        authorListFragment = new AuthorListFragment();
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                    drawerLayout, toolbar, R.string.app_name, R.string.app_name);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AuthorActivity.this, EditActivity.class);
                i.putExtra(EditActivity.EXTRA_IS_EDIT, false);
                startActivity(i);

            }
        });
        fragmentManager = getSupportFragmentManager();
        editTextSearchAuthor = (EditText) findViewById(R.id.author_search);
        editTextSearchAuthor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateFragments();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        if (frameForSong != null) {
            is_land = true;
            editTextSearchSong = (EditText) findViewById(R.id.song_search);
            editTextSearchSong.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String songFilter = editTextSearchSong.getText().toString();
                    authorListFragment.updateSongFragment(songFilter);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFragments();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_help_app: {
                Intent i = new Intent(AuthorActivity.this, HelpAndAboutAppActivity.class);
                i.putExtra(HelpAndAboutAppActivity.EXTRA_IS_HELP_ACTIVITY, true);
                startActivity(i);
                break;
            }
            case R.id.menu_about_app: {
                Intent i = new Intent(AuthorActivity.this, HelpAndAboutAppActivity.class);
                i.putExtra(HelpAndAboutAppActivity.EXTRA_IS_HELP_ACTIVITY, false);
                startActivity(i);
                break;
            }
            case R.id.menu_out_app: {
                finish();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateFragments() {
        String s = editTextSearchAuthor.getText().toString();
        authorListFragment = new AuthorListFragment();
        fragmentManager.beginTransaction().replace(R.id.content_container_author, authorListFragment).commit();
        Bundle bundle = new Bundle();
        bundle.putBoolean(AuthorListFragment.EXTRA_IS_LAND_ORIENTATION, is_land);
        bundle.putString(AuthorListFragment.EXTRA_SEARCH_FILTER, s);
        authorListFragment.setArguments(bundle);
    }

}
