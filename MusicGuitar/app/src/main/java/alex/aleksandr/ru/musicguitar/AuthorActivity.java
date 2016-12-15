package alex.aleksandr.ru.musicguitar;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;


public class AuthorActivity extends AppCompatActivity {

    private MusicListDb db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AuthorActivity.this, EditActivity.class);
                i.putExtra(EditActivity.EXTRA_IS_EDIT, false);
                startActivity(i);

            }
        });

        db = MusicListDb.getMusicDataBase(this);

        // Generation value
        try {

            for (int i = 1; i < 16; i++) {
                db.addListAuthor("Author_" + i);
                db.addListSong("Author_" + i, "NameSong_" + i, "textSong" + i);

            }
        } catch (SQLException e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.author_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cursor = db.querySelectAuthor(null, null);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(cursor);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_author, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            Intent i = new Intent(AuthorActivity.this, AboutAppActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;

        private RecyclerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView;
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(AuthorActivity.this, SongActivity.class);
            i.putExtra(SongActivity.EXTRA_NAME_AUTHOR, Author.fromCursor(cursor).getName());
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
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();
        }
    }

}
