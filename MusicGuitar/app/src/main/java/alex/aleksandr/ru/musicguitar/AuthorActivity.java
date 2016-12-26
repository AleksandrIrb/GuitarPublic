package alex.aleksandr.ru.musicguitar;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;


public class AuthorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_NAME_AUTHOR = "alex.aleksandr.ru.musicguitar.extra_name_author";

    private MusicListDb db;
    private Cursor cursor;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        db = MusicListDb.getMusicDataBase(this);
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        cursor = db.queryAuthorByName();
        recyclerView = (RecyclerView) findViewById(R.id.author_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(cursor);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.show_f1: {
                Intent i = new Intent(AuthorActivity.this, HelpActivity.class);
                startActivity(i);
                break;
            }
            case R.id.show_f2: {
                AboutAppFragment dialogWindow = new AboutAppFragment();
                dialogWindow.show(getSupportFragmentManager(), "abAppFragment");
                break;
            }
            case R.id.out_app: {
                finish();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
        private Cursor c;
        private int positionHolder;

        private RecyclerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView;
        }

        @Override
        public void onClick(View view) {
            c = cursor;
            c.moveToPosition(positionHolder);
            Intent i = new Intent(AuthorActivity.this, ContainerActivity.class);
            i.putExtra(EXTRA_NAME_AUTHOR, Author.fromCursor(cursor).getName());
            startActivity(i);
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

        private Cursor cursor;

        private RecyclerAdapter(Cursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            return new RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerHolder holder, int position) {
            cursor.moveToPosition(position);
            holder.textView.setText(Author.fromCursor(cursor).getName());
            holder.positionHolder = position;
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();
        }
    }

}
